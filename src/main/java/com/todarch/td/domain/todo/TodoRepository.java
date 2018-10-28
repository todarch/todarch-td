package com.todarch.td.domain.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository
    extends JpaRepository<TodoEntity, TodoId>, JpaSpecificationExecutor<TodoEntity> {

  /**
   * Retrieves the next id value for tag entity.
   * @deprecated do not use method, instead of nextId directly.
   */
  @Deprecated
  @Query(value = "SELECT todo_seq.nextval FROM dual", nativeQuery = true)
  Long getNextSeriesId();

  default TodoId nextId() {
    return TodoId.of(getNextSeriesId());
  }

  List<TodoEntity> findAllByUserId(Long userId);

  Optional<TodoEntity> findByIdAndUserId(TodoId todoId, Long userId);
}
