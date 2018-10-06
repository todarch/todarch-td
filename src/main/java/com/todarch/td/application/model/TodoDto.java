package com.todarch.td.application.model;

import com.todarch.td.domain.todo.model.TodoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoDto {
  private long id;
  private long userId;
  private String title;
  private String description;
  private int priority;
  private String status;
  private long timeNeededInMin;

  /**
   * Maps todoEntity to dto.
   */
  public static TodoDto from(TodoEntity todoEntity) {
    TodoDto todoDto = new TodoDto();
    todoDto.setId(todoEntity.id());
    todoDto.setUserId(todoEntity.userId());
    todoDto.setTitle(todoEntity.title());
    todoDto.setDescription(todoEntity.description());
    todoDto.setPriority(todoEntity.priority().value());
    todoDto.setStatus(todoEntity.status().name());
    todoDto.setTimeNeededInMin(todoEntity.timeNeededInMin().toMinutes());
    return todoDto;
  }
}
