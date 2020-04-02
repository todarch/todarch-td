package com.todarch.td.application.tag;

import com.todarch.td.domain.shared.Tag;
import com.todarch.td.domain.tag.TagEntity;
import com.todarch.td.domain.tag.TagRepository;
import com.todarch.td.helper.ServiceIntTest;
import com.todarch.td.helper.TestTag;
import com.todarch.td.helper.TestTodo;
import com.todarch.td.helper.TestUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

public class TagCommandManagerIntTest extends ServiceIntTest {

  @Autowired
  private TagCommandManager tagCommandManager;

  @Autowired
  private TagRepository tagRepository;

  @Test
  public void shouldUseExistingTagsInsteadOfCreatingAgain() {
    dbHelper.createTestTag();

    var taggingCmd = new TagTodoCommand();
    taggingCmd.setTodoId(TestTodo.ID);
    taggingCmd.setUserId(TestUser.ID);
    taggingCmd.setTags(TestTag.AS_SET);

    tagCommandManager.tagTodo(taggingCmd);

    Assertions.assertThat(tagRepository.count()).isEqualTo(1L);
  }

  @Test
  public void shouldUpdateTodoTags() {
    TagEntity tagEntity = dbHelper.createTestTag();
    tagEntity.assignTo(TestTodo.ID);
    tagRepository.saveAndFlush(tagEntity);

    var anotherTagEntity = new TagEntity(TestTag.nextId(), TestUser.ID, Tag.of("anotherTag"));
    anotherTagEntity.assignTo(TestTodo.ID);
    tagRepository.saveAndFlush(anotherTagEntity);

    var tagToKeep = tagEntity.name();
    var tagToRemove = anotherTagEntity.name();
    var newTag = Tag.of("newTag1");

    var newTags = Set.of(newTag, tagEntity.name());

    var cmd = new ReTaggingTodoCommand(TestTodo.ID, tagEntity.userId(), newTags);

    tagCommandManager.reTagTodo(cmd);

    var updatedTestTodoTags = tagRepository.findAllByTodoId(TestTodo.ID)
        .stream()
        .map(TagEntity::name)
        .collect(Collectors.toSet());

    Assertions.assertThat(updatedTestTodoTags).doesNotContain(tagToRemove);
    Assertions.assertThat(updatedTestTodoTags).contains(tagToKeep);
    Assertions.assertThat(updatedTestTodoTags).contains(newTag);
  }
}
