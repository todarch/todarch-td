package com.todarch.td.helper;

import com.todarch.td.domain.shared.Tag;
import com.todarch.td.domain.tag.TagId;

import java.time.Instant;
import java.util.Set;

public class TestTag {
  public static final TagId ID = TagId.of(66L);
  public static final Tag VALUE = Tag.of("testTag");
  public static final Set<Tag> AS_SET = Set.of(VALUE);

  public static TagId nextId() {
    return TagId.of(Instant.now().toEpochMilli());
  }
}
