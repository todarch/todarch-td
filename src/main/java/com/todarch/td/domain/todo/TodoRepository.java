package com.todarch.td.domain.todo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository
    extends JpaRepository<TodoEntity, Long>, JpaSpecificationExecutor<TodoEntity> {

  List<TodoEntity> findAllByUserId(Long userId);
}
