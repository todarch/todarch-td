package com.todarch.td.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDeletionCommand {
  private Long userId;
  private Long todoId;
}
