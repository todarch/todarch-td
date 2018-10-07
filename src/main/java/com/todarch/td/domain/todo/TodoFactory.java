package com.todarch.td.domain.todo;

import com.todarch.td.application.model.NewTodoCommand;

public final class TodoFactory {
  private TodoFactory() {
    throw new AssertionError("No instance of utility class");
  }

  /**
   * Creates td entity using protected setters.
   *
   * @param command data for new td
   * @return constructed td entity
   */
  public static TodoEntity from(NewTodoCommand command) {
    TodoEntity todoEntity = new TodoEntity(command.getUserId(), command.getTitle());
    todoEntity.setDescription(command.getDescription());
    todoEntity.setPriority(command.getPriority());
    todoEntity.setTimeNeededInMin(command.getTimeNeeded());
    return todoEntity;
  }
}
