package com.todarch.td.domain.tag;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TagIdTest {

  @Test
  public void valueEquality() {
    var tagId = TagId.of(5L);
    var sameTagId = TagId.of(5L);
    Assertions.assertThat(tagId).isEqualTo(sameTagId);
  }
}
