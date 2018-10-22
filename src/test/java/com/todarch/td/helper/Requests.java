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

  /**
   * TODO:selimssevgi: cannot use same newTodoReq for another insert.
   * Logic for handling same tags handling is in application service.
   * Cannot directly insert using repository.
   * Use this shortcut for now, when tags are not the part of the test cases.
   */
  public static NewTodoReq newTodoReqWithoutTags() {
    NewTodoReq newTodoReq = newTodoReq();
    newTodoReq.setTags(List.of());
    return newTodoReq;
  }

}
