package com.todarch.td.domain.todo.repository;

import com.todarch.td.domain.todo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
  List<TodoEntity> findAllByUserId(Long userId);
}
