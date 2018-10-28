package com.todarch.td.domain.tag;

import com.todarch.td.domain.todo.TodoId;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Mapping table for td and tag.
 *
 * @author selimssevgi
 */
@Table(name = "todo_tag")
@Entity
@EqualsAndHashCode
@ToString
public class TodoTag {
  @EmbeddedId
  private TodoIdTagIdPk id;

  protected TodoTag() {
    // for jpa/hibernate
  }

  TodoTag(TodoId todoId, TagId tagId) {
    this.id = new TodoIdTagIdPk(todoId, tagId);
  }

}
