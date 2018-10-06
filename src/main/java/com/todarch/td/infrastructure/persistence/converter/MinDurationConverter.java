package com.todarch.td.infrastructure.persistence.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

@Converter
public class MinDurationConverter implements AttributeConverter<Duration, Long> {
  @Override
  public Long convertToDatabaseColumn(Duration duration) {
    return duration == null ? null : duration.toMinutes();
  }

  @Override
  public Duration convertToEntityAttribute(Long dbData) {
    // uses Duration.ZERO if value is zero
    return dbData == null ? null : Duration.ofMinutes(dbData);
  }
}
