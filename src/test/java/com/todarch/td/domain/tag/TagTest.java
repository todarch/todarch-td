package com.todarch.td.domain.tag;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TagTest {

  @Test
  public void shouldOverrideEquals() {
    Tag tag1 = new Tag(5L, "tag1");
    Tag sameTag = new Tag(5L, "tag1");
    Assertions.assertThat(tag1).isEqualTo(sameTag);
  }
}
