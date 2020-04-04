package com.todarch.td.application.tag;

import com.todarch.td.domain.shared.Tag;
import com.todarch.td.domain.tag.TagEntity;
import com.todarch.td.domain.tag.TagIdGenerator;
import com.todarch.td.domain.tag.TagRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;

/**
 * Command side of the tag operations.
 *
 * @author selimssevgi
 */
@Service
@AllArgsConstructor
@Slf4j
public class TagCommandManager {

  private final TagRepository tagRepository;

  private final TagIdGenerator tagIdGenerator;

  /**
   * Tags a td item with given tag names.
   */
  @Transactional
  public void tagTodo(@NonNull TagTodoCommand tagTodoCommand) {
    tagTodoCommand.getTags().stream()
        .map(tag -> getOrCreateTag(tagTodoCommand.getUserId(), tag))
        .forEach(tagEntity -> tagEntity.assignTo(tagTodoCommand.getTodoId()));
  }

  /**
   * ReTags a td item.
   * Removes the one that are not in the new set,
   * add the new one that were not already there.
   */
  @Transactional
  public void reTagTodo(@NonNull ReTaggingTodoCommand cmd) {
    tagRepository.findAllByTodoId(cmd.getTodoId()).stream()
        .filter(Predicate.not(tagEntity -> cmd.getNewTags().contains(tagEntity.name())))
        .forEach(tagEntity -> tagEntity.unassignFrom(cmd.getTodoId()));

    cmd.getNewTags().stream()
        .map(tag -> getOrCreateTag(cmd.getUserId(), tag))
        .forEach(tagEntity -> tagEntity.assignTo(cmd.getTodoId()));
  }

  private TagEntity getOrCreateTag(String userId, Tag tag) {
    return tagRepository
        .findByUserIdAndName(userId, tag.value())
        .orElseGet(() -> tagRepository.save(newTagEntity(userId, tag)));
  }

  private TagEntity newTagEntity(String userId, Tag tag) {
    return new TagEntity(tagIdGenerator.next(), userId, tag);
  }
}
