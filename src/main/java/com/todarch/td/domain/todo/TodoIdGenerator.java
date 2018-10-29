package com.todarch.td.domain.todo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Application-side id generator for TodoEntity.
 *
 * @author selimssevgi
 */
@Component
@AllArgsConstructor
public class TodoIdGenerator {

  private final TodoIdRepository todoIdRepository;

  public TodoId next() {
    return TodoId.of(todoIdRepository.save(new TodoIdEntity()).id());
  }

}
