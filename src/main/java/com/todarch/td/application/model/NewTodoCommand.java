package com.todarch.td.application.model;

import com.todarch.td.domain.shared.Priority;
import com.todarch.td.rest.todo.model.NewTodoReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Duration;
import java.util.Objects;

@Getter
@Setter(AccessLevel.PROTECTED)
public class NewTodoCommand {
  private String title;
  private String description;
  private Priority priority;
  private Duration timeNeeded;

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
    return newTodoCommand;
  }
}
