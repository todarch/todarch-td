package com.todarch.td.domain.shared;

import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

// @RunWith(JUnit4.class)
public class TagTest {

  @Test
  public void shouldNotAcceptNull() {
    Assertions.catchThrowableOfType(() -> Tag.of(null), NullPointerException.class);
  }

  @Test
  public void shouldNotAcceptBlank() {
    var blanks = List.of("", " ", "\t", "   ");

    for (var blank : blanks) {
      try {
        Tag.of(blank);
        Assertions.fail("Expected to fail on blank: '" + blank + "'");
      } catch (IllegalArgumentException iea) {
        Assertions.assertThat(iea.getMessage()).contains("blank");
      }
    }
  }

  @Test
  public void shouldConstructTag() {
    var tag = Tag.of("tag");
    Assertions.assertThat(tag).isNotNull();
  }

  @Test
  public void valueEquality() {
    var tag = Tag.of("tag");
    var sameTag = Tag.of("tag");
    Assertions.assertThat(tag).isEqualTo(sameTag);

    var differentTag = Tag.of("anotherTag");
    Assertions.assertThat(tag).isNotEqualTo(differentTag);
  }

  @Test
  public void shouldOverrideToString() {
    var tag = Tag.of("tag");
    Assertions.assertThat(tag.toString()).isEqualTo("tag");
  }

  @Test
  public void shouldBeAccessible() {
    var tag = Tag.of("tag");
    Assertions.assertThat(tag.value()).isEqualTo("tag");
  }

  @Test
  public void shouldRemoveLeadingAndTrailingWhiteSpace() {
    Map.of(
        " leading", "leading",
        "trailing ", "trailing",
        " both ", "both"
    ).forEach((given, expected) -> {
      var constructedTag = Tag.of(given);
      Assertions.assertThat(constructedTag.value()).isEqualTo(expected);
    });
  }

  @Test
  public void shouldLowercaseValues() {
    var given = "MixedCase";
    var expected = "mixedcase";
    Assertions.assertThat(Tag.of(given).value()).isEqualTo(expected);
  }

  @Test
  public void shouldOptionallyReturnEmpty() {
    var invalidTagValue = "  ";
    Optional<Tag> ignorable = Tag.ofIgnorable(invalidTagValue);
    Assertions.assertThat(ignorable).isEmpty();

    var validTagValue = "validTag";
    Optional<Tag> nonIgnorable = Tag.ofIgnorable(validTagValue);
    Assertions.assertThat(nonIgnorable).isNotEmpty();
  }
}
