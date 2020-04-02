package com.todarch.td.application.todo;

import com.todarch.td.application.tag.TagCommandManager;
import com.todarch.td.domain.shared.Priority;
import com.todarch.td.domain.shared.Tag;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoRepository;
import com.todarch.td.domain.todo.TodoStatus;
import com.todarch.td.helper.ServiceIntTest;
import com.todarch.td.helper.TestTodo;
import com.todarch.td.helper.TestUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class TodoCommandManagerIntTest extends ServiceIntTest {

  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private TodoCommandManager todoCommandManager;

  @MockBean
  private TagCommandManager mockedTagCommandManager;

  @Test
  public void createTodoWithDefaultValues() {
    var newTodoCommand = new TodoCreationCommand(TestUser.ID, TestTodo.TITLE);
    todoCommandManager.createTodo(newTodoCommand);

    List<TodoEntity> allByUserId = todoRepository.findAllByUserId(TestUser.ID);
    Assertions.assertThat(allByUserId).isNotEmpty();
    TodoEntity createdTodo = allByUserId.get(0);

    Assertions.assertThat(createdTodo.userId()).isEqualTo(TestUser.ID);
    Assertions.assertThat(createdTodo.status()).isEqualTo(TodoStatus.INITIAL);
    Assertions.assertThat(createdTodo.timeNeededInMin()).isEqualTo(Duration.ZERO);
    Assertions.assertThat(createdTodo.priority()).isEqualTo(Priority.DEFAULT);
    Assertions.assertThat(createdTodo.priority()).isEqualTo(Priority.DEFAULT);
  }

  @Test
  public void shouldChangeTodoStatus() {
    TodoEntity testTodo = dbHelper.createTestTodo();
    Assertions.assertThat(testTodo.status()).isEqualTo(TodoStatus.INITIAL);

    var cmd = new StatusChangeCommand();
    cmd.setChangeTo(TodoStatus.DONE);
    cmd.setTodoId(testTodo.id());
    cmd.setUserId(testTodo.userId());
    todoCommandManager.changeStatus(cmd);

    TodoEntity updated = todoRepository.findById(testTodo.id()).orElse(null);
    Assertions.assertThat(updated).isNotNull();
    Assertions.assertThat(updated.status()).isEqualTo(TodoStatus.DONE);
  }

  @Test
  public void userCanDeleteJustTheirOwnTodo() {
    TodoEntity testTodo = dbHelper.createTestTodo();

    var tdc = new TodoDeletionCommand();
    tdc.setUserId(TestUser.ID);
    tdc.setTodoId(testTodo.id());

    todoCommandManager.delete(tdc);

    TodoEntity deletedTodo = todoRepository.findById(testTodo.id()).orElse(null);
    Assertions.assertThat(deletedTodo).isNull();
  }

  @Test
  public void shouldNotDeleteIfTodoDoesNotBelongToThem() {
    TodoEntity testTodo = dbHelper.createTestTodo();

    var tdc = new TodoDeletionCommand();
    tdc.setUserId(TestUser.ID + 1);
    tdc.setTodoId(testTodo.id());

    todoCommandManager.delete(tdc);

    TodoEntity notTouchedTodo = todoRepository.findById(testTodo.id()).orElse(null);
    Assertions.assertThat(notTouchedTodo).isNotNull();
  }

  @Test
  public void shouldFullyUpdateTodo() {
    TodoEntity testTodo = dbHelper.createTestTodo();

    var updateCmd = new TodoFullUpdateCommand(testTodo.userId(), "NewTitle", testTodo.id());
    updateCmd.setPriority(newPriorityValueDifferentThan(testTodo.priority()));
    updateCmd.setDescription("newDescription");
    updateCmd.setTimeNeeded(testTodo.timeNeededInMin().plusMinutes(5L));
    updateCmd.setTags(Set.of(Tag.of("NewTag1"), Tag.of("NewTag2")));

    todoCommandManager.updateTodoFully(updateCmd);

    TodoEntity updatedTodo = todoRepository.findById(testTodo.id()).orElse(null);

    Assertions.assertThat(updatedTodo.title()).isNotEqualTo(testTodo.title());
    Assertions.assertThat(updatedTodo.description()).isNotEqualTo(testTodo.description());
    Assertions.assertThat(updatedTodo.priority()).isNotEqualTo(testTodo.priority());
    Assertions.assertThat(updatedTodo.timeNeededInMin()).isNotEqualTo(testTodo.timeNeededInMin());

    // Mockito.verify(mockedTagCommandManager, Mockito.times(1)).tagTodo();
  }

  private static Priority newPriorityValueDifferentThan(Priority priority) {
    int newPriority = priority.equals(Priority.MAX)
        ? priority.value() - 1
        : priority.value() + 1;
    return Priority.of(newPriority);
  }
}
