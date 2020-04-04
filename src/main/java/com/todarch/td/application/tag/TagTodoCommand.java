package com.todarch.td.application.tag;

import com.todarch.td.domain.shared.Tag;
import com.todarch.td.domain.todo.TodoId;
import lombok.Data;

import java.util.Set;

@Data
public class TagTodoCommand {
  private String userId;
  private TodoId todoId;
  private Set<Tag> tags;
}
