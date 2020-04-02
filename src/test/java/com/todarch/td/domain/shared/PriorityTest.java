package com.todarch.td.domain.shared;

import org.assertj.core.api.Assertions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

// @RunWith(JUnit4.class)
public class PriorityTest {

  // @Rule
  // public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldProvideStaticFactoryMethod() {
    Priority.of(1);
  }

  @Test
  public void shouldAcceptHigherValuesAsMaxPriority() {
    int higherThanMax = Priority.UPPER_LIMIT + 1;

    var maxPriority = Priority.of(higherThanMax);
    Assertions.assertThat(maxPriority).isEqualTo(Priority.MAX);
  }

  @Test
  public void shouldAcceptLowerValuesAsMinPriority() {
    int lowerThanMin = Priority.LOWER_LIMIT - 1;

    var minPriority = Priority.of(lowerThanMin);
    Assertions.assertThat(minPriority).isEqualTo(Priority.MIN);
  }

  @Test
  public void shouldReturnValidObjectForValidValue() {
    Priority priority = Priority.of(5);
    Assertions.assertThat(priority).isNotNull();
  }

  @Test
  public void shouldHaveValueMethodAndShouldReturnPriorityValue() {
    Priority priority = Priority.of(8);
    Assertions.assertThat(priority.value()).isEqualTo(8);
  }

  @Test
  public void givenSamePrioritiesShouldReturnSameObjects() {
    Priority priority1 = Priority.of(Priority.DEFAULT_VALUE);
    Priority priority2 = Priority.of(Priority.DEFAULT_VALUE);

    Assertions.assertThat(priority1).isSameAs(priority2);
  }

  @Test
  public void shouldDefineCommonPriorities() {
    Priority maxPriority = Priority.MAX;
    Priority minPriority = Priority.MIN;
    Priority defaultPriority = Priority.DEFAULT;

    Assertions.assertThat(maxPriority.value()).isEqualTo(Priority.UPPER_LIMIT);
    Assertions.assertThat(minPriority.value()).isEqualTo(Priority.LOWER_LIMIT);
    Assertions.assertThat(defaultPriority.value()).isEqualTo(Priority.DEFAULT_VALUE);
  }

  @Test
  public void shouldReturnPriorityValueAsString() {
    Assertions.assertThat(Priority.MAX.toString()).isEqualTo(String.valueOf(Priority.UPPER_LIMIT));
  }

  @Test
  public void shouldBeOrdered() {
    // List.of is immutable, and sort method does sorting in-place.
    List<Priority> priorities = Arrays.asList(Priority.MIN, Priority.MAX);
    Collections.sort(priorities);
    Assertions.assertThat(priorities.get(0)).isEqualTo(Priority.MAX);
  }
}
