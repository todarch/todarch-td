package com.todarch.td.rest.todo;

import com.todarch.security.api.SecurityUtil;
import com.todarch.security.api.UserContext;
import com.todarch.td.Endpoints;
import com.todarch.td.application.TodoManager;
import com.todarch.td.application.model.ChangeStatusCommand;
import com.todarch.td.application.model.NewTodoCommand;
import com.todarch.td.application.model.TodoDeletionCommand;
import com.todarch.td.application.model.TodoDto;
import com.todarch.td.application.todo.TodoQueryManager;
import com.todarch.td.domain.todo.TodoStatus;
import com.todarch.td.rest.todo.model.NewTodoReq;
import com.todarch.td.rest.todo.model.NewTodoRes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class TodoController {

  private final TodoManager todoManager;
  private final TodoQueryManager todoQueryManager;

  /**
   * Creates a new td for current user.
   * Returns 201 and id of the newly created td.
   */
  @PostMapping(Endpoints.TODOS)
  public ResponseEntity<NewTodoRes> createTodo(@RequestBody NewTodoReq newTodoReq) {
    UserContext userContext = SecurityUtil.tryToGetUserContext();
    Long userId = userContext.getUserId();

    NewTodoCommand newTodoCommand = NewTodoCommand.from(newTodoReq);
    newTodoCommand.setUserId(userId);

    Long newTodoId = todoManager.createTodo(newTodoCommand);

    NewTodoRes newTodoRes = new NewTodoRes();
    newTodoRes.setId(newTodoId);

    return ResponseEntity.status(HttpStatus.CREATED).body(newTodoRes);
  }

  @GetMapping(Endpoints.TODOS)
  public ResponseEntity<List<TodoDto>> currentUserTodos() {
    List<TodoDto> todos = todoManager.getCurrentUserTodos();
    return ResponseEntity.ok(todos);
  }

  @GetMapping("/api/todos/{todoId}")
  public ResponseEntity<TodoDto> getTodoById(@PathVariable("todoId") Long todoId) {
    TodoDto todo = todoManager.getTodoById(todoId);
    return ResponseEntity.ok(todo);
  }

  /**
   * Change the status of todoItem.
   *
   * @param todoId id to operate on
   * @param action appropriate status to change to
   * @return updated resource
   */
  @PutMapping("/api/todos/{todoId}/{action}")
  public ResponseEntity<TodoDto> changeTodoStatus(
      @PathVariable("todoId") Long todoId,
      @PathVariable("action") String action) {
    ChangeStatusCommand csc = new ChangeStatusCommand();
    csc.setUserId(SecurityUtil.tryToGetUserContext().getUserId());
    csc.setTodoId(todoId);
    csc.setChangeTo(TodoStatus.toTodoStatus(action));

    TodoDto updatedTodo = todoManager.changeStatus(csc);
    return ResponseEntity.ok(updatedTodo);
  }

  /**
   * Deletes todoItem.
   *
   * @param todoId id to delete
   * @return no content on successful operation
   */
  @DeleteMapping("/api/todos/{todoId}")
  public ResponseEntity<Object> deleteTodo(@PathVariable("todoId") Long todoId) {
    TodoDeletionCommand tdc = new TodoDeletionCommand();
    tdc.setUserId(SecurityUtil.tryToGetUserContext().getUserId());
    tdc.setTodoId(todoId);

    todoManager.delete(tdc);
    return ResponseEntity.noContent().build();
  }

  /**
   * Handles Rsql queries.
   *
   * @param query rsql query string
   * @return a list of todoitems that matched the query,
   *         or status 400 (Bad request) if something went wrong while parsing/querying
   */
  @GetMapping(path = "/api/todos/")
  public ResponseEntity<List<TodoDto>> queryInRsql(@RequestParam(value = "q") String query) {
    try {
      List<TodoDto> result = todoQueryManager.searchByRsqlQuery(query);
      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (Exception catchAllEx) {
      log.error("Could not handle rsql query: {}", query, catchAllEx);
      return  ResponseEntity.badRequest().build();
    }
  }

}
