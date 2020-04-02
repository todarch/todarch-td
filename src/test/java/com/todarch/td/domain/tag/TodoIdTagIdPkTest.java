package com.todarch.td.domain.tag;

import com.todarch.td.helper.TestTag;
import com.todarch.td.helper.TestTodo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// @RunWith(JUnit4.class)
public class TodoIdTagIdPkTest {

  @Test
  public void shouldDoContentEquality() {
    var todoTagId = new TodoIdTagIdPk(TestTodo.ID, TestTag.ID);
    var sameTodoTagId = new TodoIdTagIdPk(TestTodo.ID, TestTag.ID);
    Assertions.assertThat(todoTagId).isEqualTo(sameTodoTagId);
  }
}
