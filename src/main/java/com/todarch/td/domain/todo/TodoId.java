package com.todarch.td.domain.todo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
public final class TodoId implements Serializable {
  private Long id;

  private TodoId(Long id) {
    this.id = id;
  }

  public static TodoId of(Long idValue) {
    return new TodoId(idValue);
  }

  public Long value() {
    return id;
  }

  protected TodoId() {
    // for jpa/hibernate
  }
}
