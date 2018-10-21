package com.todarch.td.application.todo;

import com.todarch.td.application.model.TodoDto;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoRepository;
import com.todarch.td.domain.todo.TodoStatus;
import com.todarch.td.helper.Requests;
import com.todarch.td.helper.ServiceIntTest;
import com.todarch.td.rest.todo.model.NewTodoReq;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TodoQueryManagerIntTest extends ServiceIntTest {

  @Autowired
  private TodoQueryManager todoQueryManager;

  @Autowired
  private TodoRepository todoRepository;

  @Test
  public void shouldFilterByStatus() {
    TodoEntity testTodo = dbHelper.createTestTodo();

    String initialStatusQuery = "todoStatus==INITIAL";
    String doneStatusQuery = "todoStatus==DONE";

    List<TodoDto> initialTodos = todoQueryManager.searchByRsqlQuery(initialStatusQuery);
    Assertions.assertThat(initialTodos).hasSize(1);

    List<TodoDto> doneTodos = todoQueryManager.searchByRsqlQuery(doneStatusQuery);
    Assertions.assertThat(doneTodos).isEmpty();

    testTodo.updateStatusTo(TodoStatus.DONE);
    todoRepository.saveAndFlush(testTodo);

    doneTodos = todoQueryManager.searchByRsqlQuery(doneStatusQuery);
    Assertions.assertThat(doneTodos).hasSize(1);

    initialTodos = todoQueryManager.searchByRsqlQuery(initialStatusQuery);
    Assertions.assertThat(initialTodos).isEmpty();
  }

  @Test
  public void higherPriorityNotDoneTodosQuery() {
    NewTodoReq newTodoReq = Requests.newTodoReq();
    newTodoReq.setPriority(4);
    dbHelper.createTodoOf(newTodoReq);

    String newTasksWithHighPriority = "todoStatus==INITIAL;priority>5";

    boolean lowPriority =
        todoQueryManager.searchByRsqlQuery(newTasksWithHighPriority)
            .stream()
            .map(TodoDto::getPriority)
            .anyMatch(priority -> priority < 5);

    Assertions.assertThat(lowPriority).isFalse();
  }
}
