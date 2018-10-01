package com.todarch.td.domain.todo.model;

import com.todarch.td.domain.shared.Priority;
import com.todarch.td.infrastructure.persistence.AuditEntity;
import lombok.AccessLevel;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

  protected TodoEntity() {
    // np public access
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
}
