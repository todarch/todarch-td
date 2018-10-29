package com.todarch.td.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TagIdRepository extends JpaRepository<TagIdEntity, Long> {
}
