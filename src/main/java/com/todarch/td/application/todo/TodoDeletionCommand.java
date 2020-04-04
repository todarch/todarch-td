package com.todarch.td.application.todo;

import com.todarch.td.domain.todo.TodoId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDeletionCommand {
  private String userId;
  private TodoId todoId;
}
