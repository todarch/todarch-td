package com.todarch.td.domain.todo;

import com.todarch.td.application.todo.TodoFullUpdateCommand;
import com.todarch.td.domain.shared.Priority;
import com.todarch.td.infrastructure.persistence.AuditEntity;
import com.todarch.td.infrastructure.persistence.converter.MinDurationConverter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Entity
@Table(name = "TODOS")
@Setter(AccessLevel.PROTECTED)
public class TodoEntity extends AuditEntity {

  @EmbeddedId
  private TodoId id;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Priority priority;

  @Column(nullable = false)
  @Enumerated
  private TodoStatus todoStatus;

  @Column
  @Convert(converter = MinDurationConverter.class)
  private Duration timeNeededInMin;

  // if there is no such thing as td, there is no meaning to TagEntity.
  // if there is no such thing as TagEntity, there is meaning to td.

  @Column(name = "done_date", nullable = true)
  private Instant doneDate;

  protected TodoEntity() {
    // this is for jpa
  }

  /**
   * Accepts the required fields, and initialize other parts with default values.
   *
   * @param userId owner of the todoItem
   * @param title title of the item
   */
  TodoEntity(@NonNull TodoId todoId,
             @NonNull Long userId,
             @NonNull String title) {
    this.id = todoId;
    this.userId = userId;
    this.title = title;
    this.priority = Priority.DEFAULT;
    this.description = "";
    this.timeNeededInMin = Duration.ZERO;
    this.todoStatus = TodoStatus.INITIAL;
  }

  public TodoId id() {
    return id;
  }

  public Long userId() {
    return userId;
  }

  public String title() {
    return title;
  }

  public String description() {
    return description;
  }

  public Priority priority() {
    return priority;
  }

  public TodoStatus status() {
    return todoStatus;
  }

  public Duration timeNeededInMin() {
    return timeNeededInMin;
  }

  public Optional<Instant> doneDate() {
    return Optional.ofNullable(doneDate);
  }

  /**
   * Sets time needed to complete the todoItem.
   * We may not set this value, and use zero instead.
   */
  public void setTimeNeededInMin(Duration timeNeededInMin) {
    Duration setValue = timeNeededInMin;
    if (setValue == null) {
      setValue = Duration.ZERO;
    }
    this.timeNeededInMin = setValue;
  }

  /**
   * Updates status to given todoItem status.
   *
   * @param changeTo next status of the item
   */
  public void updateStatusTo(@NonNull TodoStatus changeTo) {
    if (isDone()) {
      throw new RuntimeException("cannot update is in done state");
    }
    if (TodoStatus.INITIAL.equals(changeTo)) {
      throw new RuntimeException("cannot change to initial state");
    }

    if (TodoStatus.DONE.equals(changeTo)) {
      this.doneDate = Instant.now();
    }

    this.todoStatus = changeTo;
  }

  /**
   * Updates the values all together.
   */
  public void updateWith(TodoFullUpdateCommand cmd) {
    this.timeNeededInMin = cmd.getTimeNeeded();
    this.title = cmd.getTitle();
    this.description = cmd.getDescription();
    this.priority = cmd.getPriority();
  }

  private boolean isDone() {
    return TodoStatus.DONE.equals(todoStatus);
  }

  public boolean canBeDeletedBy(@NonNull Long userId) {
    return this.userId().equals(userId);
  }
}
