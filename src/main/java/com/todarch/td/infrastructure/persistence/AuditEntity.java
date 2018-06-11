package com.todarch.td.infrastructure.persistence;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity {

  @Column(name = "CREATED_DATE", nullable = false, updatable = false)
  @CreatedDate
  private Instant createdDate;

  @Column(name = "MODIFIED_DATE", nullable = false)
  @LastModifiedDate
  private Instant modifiedDate;

  @Column(name = "CREATED_BY")
  @CreatedBy
  private Long createdBy;

  @Column(name = "MODIFIED_BY")
  @LastModifiedBy
  private Long modifiedBy;
}
