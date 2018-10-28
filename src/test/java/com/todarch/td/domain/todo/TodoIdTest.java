package com.todarch.td.domain.todo;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TodoIdTest {

  @Test
  public void valueEquality() {
    var todoId = TodoId.of(5L);
    var sameTodoId = TodoId.of(5L);
    Assertions.assertThat(todoId).isEqualTo(sameTodoId);
  }

}
