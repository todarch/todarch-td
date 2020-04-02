package com.todarch.td.application.tag;

import com.todarch.td.domain.tag.TagRepository;
import com.todarch.td.helper.ServiceIntTest;
import com.todarch.td.helper.TestTodo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TagQueryManagerIntTest extends ServiceIntTest {

  @Autowired
  private TagQueryManager tagQueryManager;

  @Autowired
  private TagRepository tagRepository;

  @Test
  public void shouldGetTagsOfTodo() {
    var testTagEntity = dbHelper.createTestTag();
    testTagEntity.assignTo(TestTodo.ID);
    tagRepository.saveAndFlush(testTagEntity);

    var tagDto = tagQueryManager.tagsFor(TestTodo.ID).get(0);

    Assertions.assertThat(tagDto.getName()).isEqualTo(testTagEntity.name().value());
    Assertions.assertThat(tagDto.getTagId()).isEqualTo(testTagEntity.id().value());
    Assertions.assertThat(tagDto.getUserId()).isEqualTo(testTagEntity.userId());
  }
}
