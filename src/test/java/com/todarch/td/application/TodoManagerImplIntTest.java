package com.todarch.td.application;

import com.todarch.td.application.model.ChangeStatusCommand;
import com.todarch.td.application.model.NewTodoCommand;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoRepository;
import com.todarch.td.domain.todo.TodoStatus;
import com.todarch.td.helper.Requests;
import com.todarch.td.helper.ServiceIntTest;
import com.todarch.td.helper.TestUser;
import com.todarch.td.rest.todo.model.NewTodoReq;
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

  @Test
  public void shouldUseExistingTagsInsteadOfCreatingAgainAndFailingOnConstraint() {
    TodoEntity testTodo = dbHelper.createTestTodo();

    NewTodoReq newTodoReq = Requests.newTodoReq();
    NewTodoCommand newTodoCommand = NewTodoCommand.from(newTodoReq);
    newTodoCommand.setUserId(TestUser.ID);
    Long newTodoId = todoManager.createTodo(newTodoCommand);

    TodoEntity otherTodo = todoRepository.findById(newTodoId).orElse(null);
    Assertions.assertThat(otherTodo).isNotNull();
    Assertions.assertThat(otherTodo.tags()).containsExactlyElementsOf(testTodo.tags());

  }
}
