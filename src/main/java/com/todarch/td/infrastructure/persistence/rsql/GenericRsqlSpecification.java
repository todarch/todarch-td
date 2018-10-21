package com.todarch.td.infrastructure.persistence.rsql;

import com.todarch.td.domain.shared.Priority;
import com.todarch.td.domain.todo.TodoStatus;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class GenericRsqlSpecification<T> implements Specification<T> {
  private final String property;
  private final ComparisonOperator operator;
  private final List<String> arguments;

  /**
   * ComparisonNode types should implement Comparable interface.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Predicate toPredicate(
      Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

    List<Object> args = castArguments(root);
    Object argument = args.get(0);

    // RSQLOperators are not enum, they are normal objects, cannot switch over
    if (operator == RSQLOperators.EQUAL) {
      if (argument instanceof String) {
        return builder.like(
            root.get(property), argument.toString().replace('*', '%'));
      } else if (argument == null) {
        return builder.isNull(root.get(property));
      } else {
        return builder.equal(root.get(property), argument);
      }
    }

    if (operator == RSQLOperators.NOT_EQUAL) {
      if (argument instanceof String) {
        return builder.notLike(
            root.get(property), argument.toString().replace('*', '%'));
      } else if (argument == null) {
        return builder.isNotNull(root.get(property));
      } else {
        return builder.notEqual(root.get(property), argument);
      }
    }

    if (operator == RSQLOperators.GREATER_THAN) {
      return builder.greaterThan(root.get(property), (Comparable) argument);
    }

    if (operator == RSQLOperators.GREATER_THAN_OR_EQUAL) {
      return builder.greaterThanOrEqualTo(root.get(property), (Comparable) argument);
    }

    if (operator == RSQLOperators.LESS_THAN) {
      return builder.lessThan(root.get(property), (Comparable) argument);
    }

    if (operator == RSQLOperators.LESS_THAN_OR_EQUAL) {
      return builder.lessThanOrEqualTo(root.get(property), (Comparable) argument);
    }

    if (operator == RSQLOperators.IN) {
      return root.get(property).in(args);
    }

    if (operator == RSQLOperators.NOT_IN) {
      return builder.not(root.get(property).in(args));
    }

    log.debug("Could not find matching RSQLOperator for '{}'", operator);
    throw new AssertionError("Should not get here!");
  }

  private List<Object> castArguments(Root<T> root) {
    List<Object> args = new ArrayList<>();
    Class<?> type = root.get(property).getJavaType();

    for (String argument : arguments) {
      if (type.equals(Integer.class)) {
        args.add(Integer.parseInt(argument));
      } else if (type.equals(Long.class)) {
        args.add(Long.parseLong(argument));
      } else if (type.equals(TodoStatus.class)) {
        args.add(TodoStatus.valueOf(argument));
      } else if (type.equals(Priority.class)) {
        args.add(Priority.of(Integer.parseInt(argument)));
      } else {
        args.add(argument);
      }
    }

    return args;
  }
}


