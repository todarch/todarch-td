package com.todarch.td.domain.tag;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * This is the only part that should be used by client to get next id value.
 *
 * @author selimssevgi
 */
@Component
@AllArgsConstructor
public class TagIdGenerator {

  private final TagIdRepository tagIdRepository;

  public TagId next() {
    return TagId.of(tagIdRepository.save(new TagIdEntity()).id());
  }

}
