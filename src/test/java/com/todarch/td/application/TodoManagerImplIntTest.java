package com.todarch.td.application;

import com.todarch.td.application.model.ChangeStatusCommand;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoRepository;
import com.todarch.td.domain.todo.TodoStatus;
import com.todarch.td.helper.BaseIntTest;
import com.todarch.td.helper.ServiceIntTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TodoManagerImplIntTest extends ServiceIntTest {

  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private TodoManager todoManager;

  @Test
  public void shouldChangeTodoStatus() {
    TodoEntity testTodo = dbHelper.createTestTodo();
    Assertions.assertThat(testTodo.status()).isEqualTo(TodoStatus.INITIAL);

    ChangeStatusCommand cmd = new ChangeStatusCommand();
    cmd.setChangeTo(TodoStatus.DONE);
    cmd.setTodoId(testTodo.id());
    cmd.setUserId(testTodo.userId());
    todoManager.changeStatus(cmd);

    TodoEntity updated = todoRepository.findById(testTodo.id()).orElse(null);
    Assertions.assertThat(updated).isNotNull();
    Assertions.assertThat(updated.status()).isEqualTo(TodoStatus.DONE);
  }
}
