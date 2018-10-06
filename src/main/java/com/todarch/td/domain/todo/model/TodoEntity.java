package com.todarch.td.domain.todo.model;

import com.todarch.td.domain.shared.Priority;
import com.todarch.td.infrastructure.persistence.AuditEntity;
import com.todarch.td.infrastructure.persistence.converter.MinDurationConverter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Duration;

@Entity
@Table(name = "TODOS")
@Setter(AccessLevel.PROTECTED)
public class TodoEntity extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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

  protected TodoEntity() {
    this.todoStatus = TodoStatus.INITIAL;
  }

  public Long id() {
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

    this.todoStatus = changeTo;
  }

  private boolean isDone() {
    return TodoStatus.DONE.equals(todoStatus);
  }
}
