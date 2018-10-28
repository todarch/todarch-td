package com.todarch.td.helper;

import com.todarch.td.application.todo.TodoManagerMapper;
import com.todarch.td.domain.tag.TagEntity;
import com.todarch.td.domain.tag.TagRepository;
import com.todarch.td.domain.tag.TodoTagRepository;
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

  @Autowired
  private TodoTagRepository todoTagRepository;

  @Autowired
  private TodoManagerMapper todoManagerMapper;

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
    return createTodoFor(TestUser.ID, newTodoReq);
  }

  /**
   * Creates custom td item.
   *
   * @return created td object in detached state.
   */
  public TodoEntity createTodoFor(Long userId, NewTodoReq newTodoReq) {
    var newTodoCommand = todoManagerMapper.toNewTodoCommand(newTodoReq, userId);
    TodoEntity todoEntity = TodoFactory.from(newTodoCommand, TestTodo.nextId());
    return todoRepository.saveAndFlush(todoEntity);
  }

  public TagEntity createTestTag() {
    var tagEntity = new TagEntity(TestTag.ID, TestUser.ID, TestTag.VALUE);
    return tagRepository.saveAndFlush(tagEntity);
  }

  /**
   * Clears the all entries in all tables.
   * Leaves the database new and fresh for next test case.
   */
  void clearAll() {
    todoTagRepository.deleteAll(); // update todo_tag set tag_id=null where tag_id=?
    todoRepository.deleteAll();
    tagRepository.deleteAll();
  }
}

