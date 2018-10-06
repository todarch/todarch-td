package com.todarch.td.domain.tag;

import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Tag entity for grouping todoItems.
 *
 * @author selimssevgi
 */
@Entity
@Table(name = "tags")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
  @SequenceGenerator(name = "tag_generator", sequenceName = "tag_seq")
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private String name;

  protected Tag() {
  }

  public Tag(@NonNull Long userId, @NonNull String name) {
    this.userId = userId;
    this.name = name;
  }

  public Long id() {
    return id;
  }

  public String name() {
    return name;
  }
}
