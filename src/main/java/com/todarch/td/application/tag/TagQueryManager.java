package com.todarch.td.application.tag;

import com.todarch.td.domain.tag.TagEntity;
import com.todarch.td.domain.tag.TagRepository;
import com.todarch.td.domain.todo.TodoId;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Query side of the tag operations.
 *
 * @author selimssevgi
 */
@Service
@AllArgsConstructor
@Slf4j
public class TagQueryManager {

  private final TagRepository tagRepository;

  private final TagManagerMapper mapper;

  /**
   * Gets the tags for given todoId.
   *
   * @return a list of tags or empty list.
   */
  public List<TagDto> tagsFor(@NonNull TodoId todoId) {
    return tagRepository.findAllByTodoId(todoId)
        .stream()
        .map(mapper::toTagDto)
        .collect(Collectors.toList());
  }
}
