package com.todarch.td.application.todo;

import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoRepository;
import com.todarch.td.domain.todo.TodoStatus;
import com.todarch.td.helper.Requests;
import com.todarch.td.helper.ServiceIntTest;
import com.todarch.td.rest.todo.model.NewTodoReq;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TodoQueryManagerIntTest extends ServiceIntTest {

  @Autowired
  private TodoQueryManager todoQueryManager;

  @Autowired
  private TodoRepository todoRepository;

  @Test
  public void shouldFilterByStatus() {
    TodoEntity testTodo = dbHelper.createTestTodo();
    Long userId = testTodo.userId();

    String initialStatusQuery = "todoStatus==INITIAL";
    String doneStatusQuery = "todoStatus==DONE";

    List<TodoDto> initialTodos = todoQueryManager.searchByRsqlQuery(initialStatusQuery, userId);
    Assertions.assertThat(initialTodos).hasSize(1);

    List<TodoDto> doneTodos = todoQueryManager.searchByRsqlQuery(doneStatusQuery, userId);
    Assertions.assertThat(doneTodos).isEmpty();

    testTodo.updateStatusTo(TodoStatus.DONE);
    todoRepository.saveAndFlush(testTodo);

    doneTodos = todoQueryManager.searchByRsqlQuery(doneStatusQuery, userId);
    Assertions.assertThat(doneTodos).hasSize(1);

    initialTodos = todoQueryManager.searchByRsqlQuery(initialStatusQuery, userId);
    Assertions.assertThat(initialTodos).isEmpty();
  }

  @Test
  public void higherPriorityNotDoneTodosQuery() {
    NewTodoReq newTodoReq = Requests.newTodoReq();
    newTodoReq.setPriority(4);
    TodoEntity testTodo = dbHelper.createTodoOf(newTodoReq);

    String newTasksWithHighPriority = "todoStatus==INITIAL;priority>5";

    boolean lowPriority =
        todoQueryManager.searchByRsqlQuery(newTasksWithHighPriority, testTodo.userId())
            .stream()
            .map(TodoDto::getPriority)
            .anyMatch(priority -> priority < 5);

    Assertions.assertThat(lowPriority).isFalse();
  }

  @Test
  public void rsqlQueryShouldWorkOnTodosBelongingToGivenUserId() throws Exception {
    TodoEntity testTodo = dbHelper.createTestTodo();
    Long testUserId = testTodo.userId();
    Long anotherUserId = testUserId + 4;
    TodoEntity anotherUsersTodo =
        dbHelper.createTodoFor(anotherUserId, Requests.newTodoReqWithoutTags());

    String initialStatusQuery = "todoStatus==INITIAL";
    // expected to have a single unique value in these list,
    // keep it this way to make it more explicit
    List<Long> userIdsOfTodos =
        todoQueryManager.searchByRsqlQuery(initialStatusQuery, testUserId)
        .stream()
        .map(TodoDto::getUserId)
        .collect(Collectors.toList());

    Assertions.assertThat(userIdsOfTodos).doesNotContain(anotherUserId);
  }

  @Test
  public void rsqlQueryShouldWorkForDurationType() {
    TodoEntity testTodo = dbHelper.createTestTodo();
    long timeLimit = testTodo.timeNeededInMin().toMinutes() + 1;
    String rsqlQuery = "timeNeededInMin=lt=" + String.valueOf(timeLimit);

    Optional<Long> maxDuration =
        todoQueryManager.searchByRsqlQuery(rsqlQuery, testTodo.userId()).stream()
            .map(TodoDto::getTimeNeededInMin)
            .max(Comparator.comparingLong(Long::longValue));

    Assertions.assertThat(maxDuration).isNotEmpty();
    Assertions.assertThat(maxDuration.get()).isLessThan(timeLimit);
  }
}
