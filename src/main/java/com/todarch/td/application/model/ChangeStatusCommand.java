package com.todarch.td.application.model;

import com.todarch.td.domain.todo.TodoStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeStatusCommand {
  private Long userId;
  private Long todoId;
  private TodoStatus changeTo;
}
