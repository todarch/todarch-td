package com.todarch.td.helper;

import com.todarch.td.rest.todo.model.NewTodoReq;

import java.util.List;

public final class Requests {
  private Requests() {
    throw new AssertionError("No instance of utility class");
  }

  /**
   * Returns new td request.
   */
  public static NewTodoReq newTodoReq() {
    NewTodoReq newTodoReq = new NewTodoReq();
    newTodoReq.setTitle("New Title");
    newTodoReq.setDescription("New Description");
    newTodoReq.setPriority(5);
    newTodoReq.setTimeNeededInMin(63);
    newTodoReq.setTags(List.of("tag1", "tag2"));
    return newTodoReq;
  }
}
