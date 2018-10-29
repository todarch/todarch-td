package com.todarch.td.domain.todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Dummy entity to be used to retrieve next sequence id for TodoEntity.
 * It should not be outside of the package.
 *
 * @author selimssevgi
 */
@Table(name = "todo_id")
@Entity
class TodoIdEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_id_generator")
  @SequenceGenerator(
      name = "todo_id_generator",
      sequenceName = "todo_id_seq",
      // keep the values same and, different than default values not to have negative values.
      initialValue = 300,
      allocationSize = 100)
  private Long id;

  protected TodoIdEntity() {
    // for jpa/hibernate
  }

  public Long id() {
    return id;
  }
}
