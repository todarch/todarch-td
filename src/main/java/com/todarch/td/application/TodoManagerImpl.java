package com.todarch.td.application;

import com.todarch.security.api.SecurityUtil;
import com.todarch.security.api.UserContext;
import com.todarch.td.application.model.ChangeStatusCommand;
import com.todarch.td.application.model.NewTodoCommand;
import com.todarch.td.application.model.TodoDeletionCommand;
import com.todarch.td.application.model.TodoDto;
import com.todarch.td.domain.tag.Tag;
import com.todarch.td.domain.tag.TagRepository;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoFactory;
import com.todarch.td.domain.todo.TodoRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TodoManagerImpl implements TodoManager {

  private final TodoRepository todoRepository;
  private final TagRepository tagRepository;

  @Transactional
  @Override
  public Long createTodo(@NonNull NewTodoCommand newTodoCommand) {
    Long userId = newTodoCommand.getUserId();
    TodoEntity newTodo = TodoFactory.from(newTodoCommand);
    newTodoCommand.getTags()
        .stream()
        .map(tagName -> getOrCreateTag(userId, tagName))
        .forEach(newTodo::addTag);

    TodoEntity savedTodo = todoRepository.save(newTodo);
    log.info("Saved todo with id of {}", savedTodo.id());

    return savedTodo.id();
  }

  private Tag getOrCreateTag(Long userId, String tagName) {
    return tagRepository
        .findByUserIdAndName(userId, tagName)
        .orElseGet(() -> tagRepository.save(new Tag(userId, tagName)));
  }

  @Override
  public TodoDto getTodoById(@NonNull Long todoId) {
    TodoEntity todoEntity = todoRepository.findById(todoId)
        .orElseThrow(() -> new RuntimeException("Todo not found: " + todoId));

    return TodoDto.from(todoEntity);
  }

  @Override
  public List<TodoDto> getCurrentUserTodos() {
    UserContext userContext = SecurityUtil.tryToGetUserContext();
    Long userId = userContext.getUserId();
    return todoRepository.findAllByUserId(userId)
        .stream()
        .map(TodoDto::from)
        .collect(Collectors.toList());
  }

  @Override
  public TodoDto changeStatus(@NonNull ChangeStatusCommand csc) {
    TodoEntity todoEntity = todoRepository.findById(csc.getTodoId())
        .orElseThrow(() -> new RuntimeException("Resource not found"));

    todoEntity.updateStatusTo(csc.getChangeTo());
    TodoEntity reloadEntity = todoRepository.saveAndFlush(todoEntity);
    log.info("Status of TodoItem#{} changed to {}", todoEntity.id(), todoEntity.status());
    return TodoDto.from(reloadEntity);
  }

  @Override
  public void delete(@NonNull TodoDeletionCommand tdc) {
    todoRepository.findById(tdc.getTodoId())
        .ifPresent(todoEntity -> {
          if (todoEntity.canBeDeletedBy(tdc.getUserId())) {
            todoRepository.delete(todoEntity);
          }
        });
  }
}
