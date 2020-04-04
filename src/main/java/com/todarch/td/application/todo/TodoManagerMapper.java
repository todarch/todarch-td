package com.todarch.td.application.todo;

import com.todarch.td.domain.shared.Priority;
import com.todarch.td.domain.shared.Tag;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoId;
import com.todarch.td.rest.todo.model.NewTodoReq;
import com.todarch.td.rest.todo.model.TodoFullUpdateReq;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Factory utility component to create command and query models.
 *
 * @author selimssevgi
 */
@Component
public final class TodoManagerMapper {

  /**
   * Maps todoEntity to dto.
   */
  public TodoDto toTodoDto(TodoEntity todoEntity) {
    var todoDto = new TodoDto();
    todoDto.setId(todoEntity.id().value());
    todoDto.setUserId(todoEntity.userId());
    todoDto.setTitle(todoEntity.title());
    todoDto.setDescription(todoEntity.description());
    todoDto.setPriority(todoEntity.priority().value());
    todoDto.setStatus(todoEntity.status().name());
    todoDto.setTimeNeededInMin(todoEntity.timeNeededInMin().toMinutes());
    todoDto.setCreatedAtEpoch(todoEntity.getCreatedDate().toEpochMilli());
    todoDto.setDoneDateEpoch(todoEntity.doneDate().orElse(Instant.EPOCH).toEpochMilli());
    return todoDto;
  }

  /**
   * Creates a new command to fully update a td item.
   *
   * @param req possible new data
   * @param userId owner of the td item
   * @param todoId identifier of the td item
   */
  public TodoFullUpdateCommand toTodoFullUpdateCommand(@NonNull TodoFullUpdateReq req,
                                                       @NonNull String userId,
                                                       @NonNull TodoId todoId) {
    var todoFullUpdateCommand = new TodoFullUpdateCommand(userId, req.getTitle(), todoId);
    todoFullUpdateCommand.setDescription(Objects.requireNonNull(req.getDescription()));
    todoFullUpdateCommand.setPriority(Priority.of(req.getPriority()));
    todoFullUpdateCommand.setTimeNeeded(Duration.ofMinutes(req.getTimeNeededInMin()));
    todoFullUpdateCommand.setTags(processTags(req.getTags()));
    return todoFullUpdateCommand;
  }

  /**
   * Create command from request.
   */
  public TodoCreationCommand toNewTodoCommand(@NonNull NewTodoReq newTodoReq,
                                              @NonNull String userId) {
    var newTodoCommand = new TodoCreationCommand(userId, newTodoReq.getTitle());
    newTodoCommand.setDescription(newTodoReq.getDescription());
    newTodoCommand.setPriority(Priority.of(newTodoReq.getPriority()));
    newTodoCommand.setTimeNeeded(Duration.ofMinutes(newTodoReq.getTimeNeededInMin()));
    newTodoCommand.setTags(processTags(newTodoReq.getTags()));
    return newTodoCommand;
  }

  private Set<Tag> processTags(List<String> tags) {
    return Optional.ofNullable(tags)
        .orElse(Collections.emptyList())
        .stream()
        .map(Tag::ofIgnorable)
        .flatMap(Optional::stream)
        .collect(Collectors.toSet());
  }
}
