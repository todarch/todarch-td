package com.todarch.td.domain.todo;

import com.todarch.td.domain.tag.Tag;
import com.todarch.td.domain.tag.TagRepository;
import com.todarch.td.helper.RepositoryTest;
import com.todarch.td.helper.TestTodo;
import com.todarch.td.helper.TestUser;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TodoRepositoryTest extends RepositoryTest {

  @Autowired
  private TagRepository tagRepository;

  @Test
  public void persistWithTags() {
    TodoEntity todoEntity = new TodoEntity(TestUser.ID, TestTodo.TITLE);
    todoEntity.addTag("tag1");
    TodoEntity loadTodoEntity = tem.persistFlushFind(todoEntity);

    Tag savedTag = tagRepository.findByName("tag1").orElse(null);
    Assertions.assertThat(savedTag).isNotNull();
    Assertions.assertThat(savedTag.id()).isNotNull();
  }
}
