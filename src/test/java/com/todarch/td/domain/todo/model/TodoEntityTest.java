package com.todarch.td.domain.todo.model;

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
  public void todoShouldBeInitializedInInitialState() {
    TodoEntity todoEntity = new TodoEntity();
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
  public void useZeroValueDurationIfNotProvided() {
    TodoEntity todoEntity = new TodoEntity();
    todoEntity.setTimeNeededInMin(null);
    Assertions.assertThat(todoEntity.timeNeededInMin()).isEqualTo(Duration.ZERO);
  }
}
