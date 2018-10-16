package com.todarch.td.application.model;

import com.todarch.td.domain.tag.Tag;
import com.todarch.td.domain.todo.TodoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
  private List<String> tags;
  private Long createdAtEpoch;

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
    todoDto.setTags(todoEntity.tags().stream().map(Tag::name).collect(Collectors.toList()));
    todoDto.setCreatedAtEpoch(todoEntity.getCreatedDate().toEpochMilli());
    return todoDto;
  }
}
