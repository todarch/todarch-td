package com.todarch.td.helper;

import com.todarch.td.application.model.NewTodoCommand;
import com.todarch.td.domain.tag.Tag;
import com.todarch.td.domain.tag.TagRepository;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.domain.todo.TodoFactory;
import com.todarch.td.domain.todo.TodoRepository;
import com.todarch.td.rest.todo.model.NewTodoReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbHelper {
  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private TagRepository tagRepository;

  /**
   * Creates test td item.
   *
   * @return created test td object in detached state.
   */
  public TodoEntity createTestTodo() {
    NewTodoReq newTodoReq = Requests.newTodoReq();
    return createTodoOf(newTodoReq);
  }

  /**
   * Creates custom td item.
   *
   * @return created td object in detached state.
   */
  public TodoEntity createTodoOf(NewTodoReq newTodoReq) {
    NewTodoCommand newTodoCommand = NewTodoCommand.from(newTodoReq);
    newTodoCommand.setUserId(TestUser.ID);
    TodoEntity todoEntity = TodoFactory.from(newTodoCommand);
    newTodoCommand.getTags()
        .forEach(tagName -> todoEntity.addTag(new Tag(TestUser.ID, tagName)));
    return todoRepository.saveAndFlush(todoEntity);
  }

  public void clearAll() {
    todoRepository.deleteAll();
    tagRepository.deleteAll();
  }
}

