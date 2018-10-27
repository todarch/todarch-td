package com.todarch.td.application;

import com.todarch.td.application.model.ChangeStatusCommand;
import com.todarch.td.application.model.NewTodoCommand;
import com.todarch.td.application.model.TodoDeletionCommand;
import com.todarch.td.application.model.TodoDto;

public interface TodoManager {

  Long createTodo(NewTodoCommand newTodoCommand);

  TodoDto changeStatus(ChangeStatusCommand csc);

  void delete(TodoDeletionCommand tdc);
}
