package com.todarch.td.domain.shared;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Optional;

/**
 * Tag value object defines what a tag means to the system.
 *
 * @author selimssevgi
 */
@EqualsAndHashCode
public class Tag implements Serializable {
  private final String name;

  private Tag(String name) {
    this.name = name;
  }

  /**
   * Static factory method for constructing valid tags.
   * Does not accept blank text,
   * converts all text to lower case,
   * and strips the whitespace around the text.
   */
  public static Tag of(@NonNull String tagValue) {
    if (tagValue.isBlank()) {
      throw new IllegalArgumentException("Tag cannot be blank.");
    }

    var trimmedValue = tagValue.strip();
    var processedValue = trimmedValue.toLowerCase();

    return new Tag(processedValue);
  }

  /**
   * Utility method to provide a way to ignore invalid tags in the flow.
   *
   * @param tagValue possible invalid tag
   * @return empty if tag is invalid, or constructed tag wrapped on optional
   */
  public static Optional<Tag> ofIgnorable(String tagValue) {
    try {
      return Optional.of(Tag.of(tagValue));
    } catch (Exception anyException) {
      return Optional.empty();
    }
  }

  public String value() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
