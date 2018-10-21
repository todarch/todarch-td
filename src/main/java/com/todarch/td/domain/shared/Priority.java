package com.todarch.td.domain.shared;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Priority value object.
 */
public class Priority implements Comparable<Priority> {
  // this should be at the top, otherwise throws NPE when creating other objects
  private static final Map<Integer, Priority> cache = new ConcurrentHashMap<>(10);

  protected static final int UPPER_LIMIT = 10;
  protected static final int LOWER_LIMIT = 1;
  protected static final int DEFAULT_VALUE = 5;

  public static final Priority DEFAULT = Priority.of(DEFAULT_VALUE);
  public static final Priority MAX = Priority.of(UPPER_LIMIT);
  public static final Priority MIN = Priority.of(LOWER_LIMIT);

  private final int value;


  private Priority(int value) {
    this.value = value;
  }

  /**
   * Static factory method to create a Priority Value Object.
   * It uses cache object, because we have the guarantee of limited range.
   *
   * @param priority int value of value, bigger value means higher value
   * @return valid Priority object
   * @throws IllegalArgumentException when given value is not in the range.
   */
  public static Priority of(int priority) {
    checkArgument(priority <= UPPER_LIMIT, "Priority cannot be higher than:" + UPPER_LIMIT);
    checkArgument(priority >= LOWER_LIMIT, "Priority cannot be lower than:" + LOWER_LIMIT);

    if (cache.containsKey(priority)) {
      return cache.get(priority);
    }

    Priority priorityObj = new Priority(priority);
    cache.put(priority, priorityObj);
    return priorityObj;
  }

  public int value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Priority priority1 = (Priority) o;
    return value == priority1.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  /**
   * Priority value is like any other int or long value.
   * This method returns that value in string format.
   *
   * @return string value of priority value
   */
  @Override
  public String toString() {
    return String.valueOf(value());
  }

  /**
   * Highest priority(10) comes before other priorities.
   * It should be reverse of natural ordering of integer values.
   */
  @Override
  public int compareTo(Priority other) {
    return Integer.compare(this.value, other.value) * -1;
  }
}
