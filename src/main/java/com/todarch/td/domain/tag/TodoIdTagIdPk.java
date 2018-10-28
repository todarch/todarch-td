package com.todarch.td.domain.tag;

import com.todarch.td.domain.todo.TodoId;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * TodoId and TagId together form the primary key of mapping table.
 *
 * @author selimssevgi
 */
@Embeddable
@EqualsAndHashCode
@ToString
class TodoIdTagIdPk implements Serializable {

  @Column(name = "todo_id")
  private Long todoId = -1L;

  @Column(name = "tag_id")
  private Long tagId = -1L;

  protected TodoIdTagIdPk() {
    // for jpa/hibernate
  }

  TodoIdTagIdPk(TodoId todoId, TagId tagId) {
    this.todoId = todoId.value();
    this.tagId = tagId.value();
  }
}
