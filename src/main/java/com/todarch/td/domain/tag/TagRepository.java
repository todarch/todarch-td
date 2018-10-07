package com.todarch.td.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  /**
   * Different users can have same tags.
   * Neither of the values is unique for its column,
   * but together they are unique.
   */
  Optional<Tag> findByUserIdAndName(Long userId, String tagName);
}
