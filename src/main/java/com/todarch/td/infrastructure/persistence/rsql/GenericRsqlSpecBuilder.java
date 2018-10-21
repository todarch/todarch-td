package com.todarch.td.infrastructure.persistence.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class GenericRsqlSpecBuilder<T> {

  /**
   * "todoStatus=='INITIAL';priority=gt='5'".
   */
  Specification<T> createSpec(LogicalNode logicalNode) {

    List<Specification<T>> specs = logicalNode.getChildren()
        .stream()
        .map(this::createSpec)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    Specification<T> result = specs.get(0);

    if (logicalNode.getOperator() == LogicalOperator.AND) {
      for (int i = 1; i < specs.size(); i++) {
        result = Specification.where(result).and(specs.get(i));
      }
    } else if (logicalNode.getOperator() == LogicalOperator.OR) {
      for (int i = 1; i < specs.size(); i++) {
        result = Specification.where(result).or(specs.get(i));
      }
    }

    return result;
  }

  /**
   * "todoStatus=='INITIAL'".
   */
  Specification<T> createSpec(ComparisonNode comparisonNode) {
    return Specification.where(
        new GenericRsqlSpecification<>(
            comparisonNode.getSelector(), // todoStatus
            comparisonNode.getOperator(), // ==
            comparisonNode.getArguments() // INITIAL
        )
    );
  }

  private Specification<T> createSpec(Node node) {
    if (node instanceof LogicalNode) {
      return createSpec((LogicalNode) node);
    }
    if (node instanceof ComparisonNode) {
      return createSpec((ComparisonNode) node);
    }
    return null;
  }
}

