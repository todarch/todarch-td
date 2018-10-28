package com.todarch.td.domain.tag;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
public final class TagId implements Serializable {
  private Long id;

  private TagId(Long id) {
    this.id = id;
  }

  public static TagId of(Long idValue) {
    return new TagId(idValue);
  }

  public Long value() {
    return id;
  }

  protected TagId() {
    // for jpa/hibernate
  }
}
