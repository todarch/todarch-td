package com.todarch.td.application.todo;

import com.todarch.td.application.tag.TagCommandManager;
import com.todarch.td.application.tag.TagTodoCommand;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoFactory;
import com.todarch.td.domain.todo.TodoId;
import com.todarch.td.domain.todo.TodoIdGenerator;
import com.todarch.td.domain.todo.TodoRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class TodoCommandManager {

  private final TodoRepository todoRepository;

  private final TodoIdGenerator todoIdGenerator;

  private final TagCommandManager tagCommandManager;


  /**
   * Creates a new td with given and other default values, including tags if exist.
   *
   * @return resource id of new td item
   */
  @Transactional
  public Long createTodo(@NonNull TodoCreationCommand todoCreationCommand) {
    Long userId = todoCreationCommand.getUserId();
    TodoEntity newTodo = TodoFactory.from(todoCreationCommand, todoIdGenerator.next());
    TodoEntity savedTodo = todoRepository.save(newTodo);
    log.info("Saved todo with id of {}", savedTodo.id());

    if (!todoCreationCommand.getTags().isEmpty()) {
      var taggingCmd = new TagTodoCommand();
      taggingCmd.setUserId(userId);
      taggingCmd.setTodoId(newTodo.id());
      taggingCmd.setTags(todoCreationCommand.getTags());
      tagCommandManager.tagTodo(taggingCmd);
    }

    return savedTodo.id().value();
  }

  /**
   * Changes the status of td item if it is not already done.
   */
  public void changeStatus(@NonNull StatusChangeCommand csc) {
    TodoEntity todoEntity = todoRepository.findById(csc.getTodoId())
        .orElseThrow(() -> new RuntimeException("Resource not found"));

    todoEntity.updateStatusTo(csc.getChangeTo());
    todoRepository.saveAndFlush(todoEntity);
    log.info("Status of TodoItem#{} changed to {}", todoEntity.id(), todoEntity.status());
  }

  /**
   * Deletes the user's td item.
   */
  public void delete(@NonNull TodoDeletionCommand tdc) {
    todoRepository.findById(tdc.getTodoId())
        .ifPresent(todoEntity -> {
          if (todoEntity.canBeDeletedBy(tdc.getUserId())) {
            todoRepository.delete(todoEntity);
          }
        });
  }

  /**
   * Updates all of the values all together if it can.
   */
  public void updateTodoFully(@NonNull TodoFullUpdateCommand tfuc) {
    TodoEntity todoEntity = tryToFindTodoByIdAndUserId(tfuc.getTodoId(), tfuc.getUserId());
    todoEntity.updateWith(tfuc);

    todoRepository.save(todoEntity);
  }

  private TodoEntity tryToFindTodoByIdAndUserId(TodoId todoId, Long userId) {
    return todoRepository.findByIdAndUserId(todoId, userId)
        .orElseThrow(() -> new RuntimeException("Resource not found"));
  }
}
