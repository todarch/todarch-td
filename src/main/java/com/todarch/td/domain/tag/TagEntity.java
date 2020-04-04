package com.todarch.td.domain.tag;

import com.todarch.td.domain.shared.Tag;
import com.todarch.td.domain.todo.TodoId;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Tag entity for grouping todoItems.
 *
 * @author selimssevgi
 */
@Entity
@Table(name = "tags")
public class TagEntity {

  @EmbeddedId
  private TagId id;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false)
  private String name;

  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  // without insertable and updatable parts, firstly it tries to update tag_id to null and fails
  @JoinColumn(name = "tag_id", insertable = false, updatable = false)
  private Set<TodoTag> taggedTodos = new HashSet<>();

  Set<TodoTag> taggedTodos() {
    return Collections.unmodifiableSet(taggedTodos);
  }

  protected TagEntity() {
    // for jpa
  }

  /**
   * Accepts the minimum number of required values.
   */
  public TagEntity(@NonNull TagId tagId,
                   @NonNull String userId,
                   @NonNull Tag tag) {
    this.id = tagId;
    this.userId = userId;
    this.name = tag.value();
  }

  public TagId id() {
    return this.id;
  }

  public Tag name() {
    return Tag.of(name);
  }

  public String userId() {
    return userId;
  }

  public void assignTo(@NonNull TodoId todoId) {
    taggedTodos.add(new TodoTag(todoId, this.id));
  }

  public void unassignFrom(@NonNull TodoId todoId) {
    taggedTodos.remove(new TodoTag(todoId, this.id));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    var tagEntity = (TagEntity) o;
    return Objects.equals(id, tagEntity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TagEntity{");
    sb.append("id=").append(id);
    sb.append(", userId=").append(userId);
    sb.append(", name='").append(name).append('\'');
    sb.append(", taggedTodos=").append(taggedTodos);
    sb.append('}');
    return sb.toString();
  }
}
