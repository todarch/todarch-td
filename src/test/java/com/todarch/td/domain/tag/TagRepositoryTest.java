package com.todarch.td.domain.tag;

import com.todarch.td.domain.shared.Tag;
import com.todarch.td.helper.RepositoryTest;
import com.todarch.td.helper.TestTag;
import com.todarch.td.helper.TestTodo;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;

import java.util.List;

import static org.junit.Assert.fail;

public class TagRepositoryTest extends RepositoryTest {

  @Autowired
  private TagRepository tagRepository;

  @Test
  public void userIdAndNameTogetherShouldBeUnique() {
    TagEntity tag = new TagEntity(TestTag.ID,5L, TestTag.VALUE);
    TagEntity sameTag = new TagEntity(TestTag.ID, 5L, TestTag.VALUE);
    tem.persistAndFlush(tag);
    try {
      tem.persistAndFlush(sameTag);
      fail("User id and tag name together must be unique.");
    } catch (PersistenceException ignore) {
      //
    }
  }

  @Test
  public void shouldFindAllByTodoId() {
    var nonExistingTodoTags = tagRepository.findAllByTodoId(TestTodo.nextId());
    Assertions.assertThat(nonExistingTodoTags).isEmpty();

    var tagEntity = new TagEntity(TestTag.ID,5L, TestTag.VALUE);
    tagEntity.assignTo(TestTodo.ID);
    tem.persistFlushFind(tagEntity);

    var anotherUnrelatedTagEntity = new TagEntity(TestTag.nextId(),6L, Tag.of("Tag3"));
    anotherUnrelatedTagEntity.assignTo(TestTodo.nextId());
    tem.persistFlushFind(anotherUnrelatedTagEntity);

    var allTagsOfTestTodo = tagRepository.findAllByTodoId(TestTodo.ID);
    Assertions.assertThat(allTagsOfTestTodo).hasSize(1);
    Assertions.assertThat(allTagsOfTestTodo.get(0).id()).isEqualTo(tagEntity.id());
  }

  @Test
  public void shouldRemoveUnassignedTag() {
    var tagEntity = new TagEntity(TestTag.ID,5L, TestTag.VALUE);
    tagEntity.assignTo(TestTodo.ID);
    tem.persistFlushFind(tagEntity);

    var sameTagEntity = tagRepository.findById(tagEntity.id()).get();
    sameTagEntity.unassignFrom(TestTodo.ID);
    tem.persistFlushFind(sameTagEntity);

    var finalTagEntity = tagRepository.findById(tagEntity.id()).get();
    Assertions.assertThat(finalTagEntity.taggedTodos()).isEmpty();
  }
}
