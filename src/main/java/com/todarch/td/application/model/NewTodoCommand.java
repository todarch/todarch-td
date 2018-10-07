package com.todarch.td.application.model;

import com.todarch.td.domain.shared.Priority;
import com.todarch.td.rest.todo.model.NewTodoReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PROTECTED)
public class NewTodoCommand {
  private String title;
  private String description;
  private Priority priority;
  private Duration timeNeeded;
  private Set<String> tags;
  private Long userId;

  protected NewTodoCommand() {

  }

  /**
   * Create command from request.
   */
  public static NewTodoCommand from(@NonNull NewTodoReq newTodoReq) {
    NewTodoCommand newTodoCommand = new NewTodoCommand();
    newTodoCommand.setTitle(Objects.requireNonNull(newTodoReq.getTitle()));
    newTodoCommand.setDescription(Objects.requireNonNull(newTodoReq.getDescription()));
    newTodoCommand.setPriority(Priority.of(newTodoReq.getPriority()));
    newTodoCommand.setTimeNeeded(Duration.ofMinutes(newTodoReq.getTimeNeededInMin()));
    Set<String> processedTags = Optional.ofNullable(newTodoReq.getTags())
        .orElse(Collections.emptyList())
        .stream()
        .map(String::toLowerCase)
        .collect(Collectors.toSet());
    newTodoCommand.setTags(processedTags);
    return newTodoCommand;
  }

  //TODO:selimssevgi: this value if mandatory, should be constructed in optional way
  public void setUserId(@NonNull Long userId) {
    this.userId = userId;
  }
}
