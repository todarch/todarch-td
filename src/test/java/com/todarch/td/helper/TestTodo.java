package com.todarch.td.helper;

import com.todarch.td.domain.shared.Priority;
import com.todarch.td.domain.todo.TodoId;

import java.time.Instant;

public class TestTodo {
  public static final String TITLE = "TestTodoTitle";
  public static final String DESC = "TestTodoDesc";
  public static final TodoId ID = TodoId.of(63L);
  public static final Priority PRIORITY = Priority.DEFAULT;

  public static TodoId nextId() {
    return TodoId.of(Instant.now().toEpochMilli());
  }
}
