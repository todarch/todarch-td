package com.todarch.td.domain.tag;

import com.todarch.td.domain.todo.TodoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, TagId> {

  @Query(value = "SELECT tag_seq.nextval FROM dual", nativeQuery = true)
  Long getNextSeriesId();

  default TagId nextId() {
    return TagId.of(getNextSeriesId());
  }

  /**
   * Different users can have same tags.
   * Neither of the values is unique for its column,
   * but together they are unique.
   */
  Optional<TagEntity> findByUserIdAndName(Long userId, String tagName);

  @Query("SELECT te FROM TagEntity te INNER JOIN te.taggedTodos tt WHERE tt.id.todoId = :todoId")
  List<TagEntity> findAllByTodoId(@Param("todoId") Long todoId);

  default List<TagEntity> findAllByTodoId(TodoId todoId) {
    return findAllByTodoId(todoId.value());
  }

}
