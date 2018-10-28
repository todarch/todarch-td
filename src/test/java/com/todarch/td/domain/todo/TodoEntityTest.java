package com.todarch.td.domain.todo;

import com.todarch.td.domain.shared.Priority;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoStatus;
import com.todarch.td.helper.TestTodo;
import com.todarch.td.helper.TestUser;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Duration;

import static org.junit.Assert.fail;

/**
 * TodoEntity unit test.
 *
 * @author selimssevgi
 */
@RunWith(JUnit4.class)
public class TodoEntityTest {

  @Test
  public void shouldBeInitializedWithDefaultValues() {
    TodoEntity todoEntity = new TodoEntity(TestTodo.ID, TestUser.ID, TestTodo.TITLE);

    Assertions.assertThat(todoEntity.description()).isEmpty();
    Assertions.assertThat(todoEntity.timeNeededInMin()).isEqualTo(Duration.ZERO);
    Assertions.assertThat(todoEntity.priority()).isEqualTo(Priority.DEFAULT);
    Assertions.assertThat(todoEntity.status()).isEqualTo(TodoStatus.INITIAL);
  }

  @Test
  public void todoStatusCannotBeUpdatedToInitialStatus() {
    TodoEntity todoEntity = new TodoEntity();
    try {
      todoEntity.updateStatusTo(TodoStatus.INITIAL);
      fail("Should not be able to change to initial state");
    } catch (Exception ex) {
      Assertions.assertThat(ex.getMessage()).contains("cannot change to initial state");
    }
  }

  @Test
  public void cannotUpdateStatusIfAlreadyDone() {
    TodoEntity todoEntity = new TodoEntity();
    todoEntity.setTodoStatus(TodoStatus.DONE);
    try {
      todoEntity.updateStatusTo(TodoStatus.DONE);
      fail("Cannot update todo item if is already in done state");
    } catch (Exception ex) {
      Assertions.assertThat(ex.getMessage()).contains("cannot update is in done state");
    }
  }

  @Test
  public void shouldUpdateStatus() {
    TodoEntity todoEntity = new TodoEntity();
    todoEntity.updateStatusTo(TodoStatus.DONE);
    Assertions.assertThat(todoEntity.status()).isEqualTo(TodoStatus.DONE);
  }

  @Test
  public void shouldSetDoneDateWhenStatusChangedToDone() {
    TodoEntity todoEntity = new TodoEntity();
    Assertions.assertThat(todoEntity.doneDate()).isEmpty();
    todoEntity.updateStatusTo(TodoStatus.DONE);
    Assertions.assertThat(todoEntity.doneDate()).isNotEmpty();
  }

  @Test
  public void useZeroValueDurationIfNotProvided() {
    TodoEntity todoEntity = new TodoEntity();
    todoEntity.setTimeNeededInMin(null);
    Assertions.assertThat(todoEntity.timeNeededInMin()).isEqualTo(Duration.ZERO);
  }
}
