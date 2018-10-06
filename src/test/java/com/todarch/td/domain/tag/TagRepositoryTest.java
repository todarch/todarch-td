package com.todarch.td.domain.tag;

import com.todarch.td.helper.RepositoryTest;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.junit.Assert.fail;

public class TagRepositoryTest extends RepositoryTest {

  @Test
  public void userIdAndNameTogetherShouldBeUnique() {
    Tag tag = new Tag(5L, "Tag1");
    Tag sameTag = new Tag(5L, "Tag1");
    tem.persistAndFlush(tag);
    try {
      tem.persistAndFlush(sameTag);
      fail("User id and tag name together must be unique.");
    } catch (PersistenceException ignore) {
      //
    }
  }
}
