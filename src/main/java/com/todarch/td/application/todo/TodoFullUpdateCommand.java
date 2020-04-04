package com.todarch.td.application.todo;

import com.todarch.td.domain.todo.TodoId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class TodoFullUpdateCommand extends TodoCreationCommand {
  private TodoId todoId;

  public TodoFullUpdateCommand(String userId, String title, TodoId todoId) {
    super(userId, title);
    this.todoId = todoId;
  }
}
