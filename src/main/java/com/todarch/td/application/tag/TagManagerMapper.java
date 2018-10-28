package com.todarch.td.application.tag;

import com.todarch.td.domain.tag.TagEntity;
import org.springframework.stereotype.Component;

@Component
public final class TagManagerMapper {

  TagDto toTagDto(TagEntity tagEntity) {
    var tagDto = new TagDto();
    tagDto.setTagId(tagEntity.id().value());
    tagDto.setName(tagEntity.name().value());
    tagDto.setUserId(tagEntity.userId());
    return tagDto;
  }
}
