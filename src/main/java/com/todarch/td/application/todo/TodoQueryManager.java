package com.todarch.td.application.todo;

import com.todarch.td.application.model.TodoDto;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoRepository;
import com.todarch.td.infrastructure.persistence.rsql.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Read-only side of todoitems.
 *
 * @author selimssevgi
 */
@Service
@AllArgsConstructor
@Slf4j
public class TodoQueryManager {

  private final TodoRepository todoRepository;

  /**
   * Searches items matching the rsql query.
   * https://aboullaite.me/rsql/
   * https://www.baeldung.com/rest-api-search-language-rsql-fiql
   *
   * @param rsqlQuery rsql valid query
   * @return items matching specification constructed of query
   */
  public List<TodoDto> searchByRsqlQuery(String rsqlQuery, @NonNull Long userId) {
    String userScopedRsql = "userId==" + String.valueOf(userId) + ";" + rsqlQuery;
    Node rootNode = new RSQLParser().parse(userScopedRsql);
    Specification<TodoEntity> spec = rootNode.accept(new CustomRsqlVisitor<>());

    return todoRepository.findAll(spec)
        .stream()
        .map(TodoDto::from)
        .collect(Collectors.toList());
  }

  /**
   * Looks for a single td item by its id and user id.
   */
  public Optional<TodoDto> getUserTodoById(@NonNull Long todoId, Long userId) {
    String todoByIdQuery = "id==" + String.valueOf(todoId);
    List<TodoDto> todoDtos = searchByRsqlQuery(todoByIdQuery, userId);

    if (todoDtos.isEmpty()) {
      return Optional.empty();
    }

    if (todoDtos.size() > 1) {
      log.debug("ById search should not return more than one result: {}", todoByIdQuery);
    }

    return Optional.of(todoDtos.get(0));
  }

  /**
   * Gets all of items by a user id.
   */
  public List<TodoDto> getAllTodosByUserId(@NonNull Long userId) {
    return todoRepository.findAllByUserId(userId)
        .stream()
        .map(TodoDto::from)
        .collect(Collectors.toList());
  }

}
