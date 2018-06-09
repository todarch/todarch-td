package com.todarch.td.helper;

import com.todarch.security.api.JwtUtil;
import com.todarch.td.application.model.NewTodoCommand;
import com.todarch.td.domain.todo.model.TodoEntity;
import com.todarch.td.domain.todo.model.TodoFactory;
import com.todarch.td.domain.todo.repository.TodoRepository;
import com.todarch.td.rest.TestUser;
import com.todarch.td.rest.todo.model.NewTodoReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbHelper {
  @Autowired
  private TodoRepository todoRepository;

  /**
   * Creates test td item.
   *
   * @return created test td object in detached state.
   */
  public TodoEntity createTestTodo() {
    NewTodoReq newTodoReq = Requests.newTodoReq();
    TodoEntity todoEntity = TodoFactory.from(NewTodoCommand.from(newTodoReq), TestUser.ID);
    return todoRepository.saveAndFlush(todoEntity);
  }

  public void clearAll() {
    todoRepository.deleteAll();
  }
}

