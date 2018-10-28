package com.todarch.td.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * It is just defined to delete all entries during testing.
 *
 * @author selimssevgi
 */
@Repository
public interface TodoTagRepository extends JpaRepository<TodoTag, TodoIdTagIdPk> {
}
