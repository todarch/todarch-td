package com.todarch.td.domain.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository
    extends JpaRepository<TodoEntity, TodoId>, JpaSpecificationExecutor<TodoEntity> {

  List<TodoEntity> findAllByUserId(Long userId);

  Optional<TodoEntity> findByIdAndUserId(TodoId todoId, Long userId);
}
