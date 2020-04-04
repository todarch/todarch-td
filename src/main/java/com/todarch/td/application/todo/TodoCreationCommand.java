package com.todarch.td.application.todo;

import com.todarch.td.domain.shared.Priority;
import com.todarch.td.domain.shared.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter(AccessLevel.PROTECTED)
public class TodoCreationCommand {
  private String title;
  private String description;
  private Priority priority;
  private Duration timeNeeded;
  private Set<Tag> tags;
  private String userId;

  /**
   * Creates TodoCreationCommand with default values.
   * Use set methods to override.
   */
  TodoCreationCommand(String userId, String title) {
    this.title = title;
    this.userId = userId;
    this.priority = Priority.DEFAULT;
    this.description = "";
    this.timeNeeded = Duration.ZERO;
    this.tags = new HashSet<>();
  }
}
