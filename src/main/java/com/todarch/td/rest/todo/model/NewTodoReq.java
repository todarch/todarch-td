package com.todarch.td.rest.todo.model;

import lombok.Data;

@Data
public class NewTodoReq {
  private String title;
  private String description;
  private int priority;
  private long timeNeededInMin;
}
