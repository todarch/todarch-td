package com.todarch.td.application;

import com.todarch.td.application.model.ChangeStatusCommand;
import com.todarch.td.application.model.NewTodoCommand;
import com.todarch.td.application.model.TodoDto;

import java.util.List;

public interface TodoManager {

  Long createTodo(NewTodoCommand newTodoCommand);

  //TODO:selimssevgi: could return optional
  TodoDto getTodoById(Long todoId);

  List<TodoDto> getCurrentUserTodos();

  TodoDto changeStatus(ChangeStatusCommand csc);
}
