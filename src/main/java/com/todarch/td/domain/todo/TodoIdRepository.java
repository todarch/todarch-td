package com.todarch.td.domain.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TodoIdRepository extends JpaRepository<TodoIdEntity, Long> {
}
