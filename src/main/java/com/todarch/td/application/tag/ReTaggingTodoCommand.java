package com.todarch.td.application.tag;

import com.todarch.td.domain.shared.Tag;
import com.todarch.td.domain.todo.TodoId;
import lombok.Getter;
import lombok.NonNull;

import java.util.Set;

@Getter
public class ReTaggingTodoCommand {
  private final TodoId todoId;
  private final Long userId;
  private Set<Tag> newTags;

  ReTaggingTodoCommand(@NonNull TodoId todoId,
                       @NonNull Long userId,
                       @NonNull Set<Tag> newTags) {
    this.todoId = todoId;
    this.userId = userId;
    this.newTags = newTags;
  }
}
