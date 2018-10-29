package com.todarch.td.domain.tag;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Dummy entity to be used to retrieve next sequence id for TagEntity.
 * It should not be outside of the package.
 *
 * @author selimssevgi
 */
@Table(name = "tag_id")
@Entity
class TagIdEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_generator")
  @SequenceGenerator(
      name = "tag_id_generator",
      sequenceName = "tag_id_seq",
      initialValue = 300,
      allocationSize = 100)
  private Long id;

  protected TagIdEntity() {
    // for jpa/hibernate
  }

  public Long id() {
    return id;
  }
}
