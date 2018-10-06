package com.todarch.td.domain.todo;

/**
 * Defines statuses that an TodoItem can be in.
 *
 * @author selimssevgi
 */
public enum  TodoStatus {
  INITIAL,
  DONE;


  public static TodoStatus toTodoStatus(String action) {
    return valueOf(action.toUpperCase());
  }
}
