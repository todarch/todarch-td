package com.todarch.td.domain.tag;

import com.todarch.td.domain.shared.Tag;
import com.todarch.td.helper.TestTag;
import com.todarch.td.helper.TestTodo;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TagEntityTest {

  @Test
  public void shouldOverrideEqualsJustForId() {
    var tag1 = new TagEntity(TestTag.ID, 5L, Tag.of("tag1"));
    var sameTag = new TagEntity(TestTag.ID, 6L, Tag.of("tag2"));
    Assertions.assertThat(tag1).isEqualTo(sameTag);

    var differentTag = new TagEntity(TestTag.nextId(), 5L, Tag.of("tag1"));
    Assertions.assertThat(tag1).isNotEqualTo(differentTag);
  }

  @Test
  public void doNotAssignSameTagAgain() {
    var tag1 = new TagEntity(TestTag.ID, 5L, TestTag.VALUE);
    tag1.assignTo(TestTodo.ID);
    tag1.assignTo(TestTodo.ID);
    Assertions.assertThat(tag1.taggedTodos()).hasSize(1);
  }

  @Test
  public void unassingTagFromTodo() {
    var tag1 = new TagEntity(TestTag.ID, 5L, TestTag.VALUE);
    tag1.assignTo(TestTodo.ID);
    Assertions.assertThat(tag1.taggedTodos()).hasSize(1);
    tag1.unassignFrom(TestTodo.ID);
    Assertions.assertThat(tag1.taggedTodos()).isEmpty();
  }
}
