package com.todarch.td.application.todo;

import com.todarch.td.domain.todo.TodoId;
import com.todarch.td.domain.todo.TodoStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusChangeCommand {
  private String userId;
  private TodoId todoId;
  private TodoStatus changeTo;
}
