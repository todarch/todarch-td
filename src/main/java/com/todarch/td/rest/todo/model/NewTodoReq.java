package com.todarch.td.rest.todo.model;

import lombok.Data;

import java.util.List;

@Data
public class NewTodoReq {
  private String title;
  private String description;
  private int priority;
  private long timeNeededInMin;
  private List<String> tags;
}
