package com.todarch.td.application.todo;

import lombok.Data;

import java.util.List;

@Data
public class TodoDto {
  private long id;
  private String userId;
  private String title;
  private String description;
  private int priority;
  private String status;
  private long timeNeededInMin;
  private Long createdAtEpoch;
  private Long doneDateEpoch;
  private List<String> tags;
}
