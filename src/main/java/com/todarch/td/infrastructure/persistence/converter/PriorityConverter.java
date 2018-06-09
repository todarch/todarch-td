package com.todarch.td.infrastructure.persistence.converter;

import com.todarch.td.domain.shared.Priority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PriorityConverter implements AttributeConverter<Priority, Integer> {
  @Override
  public Integer convertToDatabaseColumn(Priority attribute) {
    if (attribute != null) {
      return attribute.value();
    }
    return null;
  }

  @Override
  public Priority convertToEntityAttribute(Integer dbData) {
    if (dbData != null) {
      return Priority.of(dbData);
    }
    return null;
  }
}
