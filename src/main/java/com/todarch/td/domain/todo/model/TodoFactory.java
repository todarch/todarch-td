package com.todarch.td.domain.todo.model;

import com.todarch.td.application.model.NewTodoCommand;

public final class TodoFactory {
  private TodoFactory() {
    throw new AssertionError("No instance of utility class");
  }

  /**
   * Creates td entity using protected setters.
   *
   * @param command data for new td
   * @param userId associates td with
   * @return constructed td entity
   */
  public static TodoEntity from(NewTodoCommand command, Long userId) {
    TodoEntity todoEntity = new TodoEntity();
    todoEntity.setUserId(userId);
    todoEntity.setTitle(command.getTitle());
    todoEntity.setDescription(command.getDescription());
    todoEntity.setPriority(command.getPriority());
    return todoEntity;
  }
}
