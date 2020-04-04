package com.todarch.td.domain.tag;

import com.todarch.td.domain.shared.Tag;
import com.todarch.td.helper.TestTag;
import com.todarch.td.helper.TestTodo;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TagEntityTest {

  private String USER_ID = UUID.randomUUID().toString();
  private String ANOTHER_USER_ID = UUID.randomUUID().toString();

  @Test
  public void shouldOverrideEqualsJustForId() {
    var tag1 = new TagEntity(TestTag.ID, USER_ID, Tag.of("tag1"));
    var sameTag = new TagEntity(TestTag.ID, ANOTHER_USER_ID, Tag.of("tag2"));
    Assertions.assertThat(tag1).isEqualTo(sameTag);

    var differentTag = new TagEntity(TestTag.nextId(), USER_ID, Tag.of("tag1"));
    Assertions.assertThat(tag1).isNotEqualTo(differentTag);
  }

  @Test
  public void doNotAssignSameTagAgain() {
    var tag1 = new TagEntity(TestTag.ID, USER_ID, TestTag.VALUE);
    tag1.assignTo(TestTodo.ID);
    tag1.assignTo(TestTodo.ID);
    Assertions.assertThat(tag1.taggedTodos()).hasSize(1);
  }

  @Test
  public void unassingTagFromTodo() {
    var tag1 = new TagEntity(TestTag.ID, USER_ID, TestTag.VALUE);
    tag1.assignTo(TestTodo.ID);
    Assertions.assertThat(tag1.taggedTodos()).hasSize(1);
    tag1.unassignFrom(TestTodo.ID);
    Assertions.assertThat(tag1.taggedTodos()).isEmpty();
  }
}
