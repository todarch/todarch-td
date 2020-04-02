package com.todarch.td.infrastructure.persistence;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {
  @Override
  public Optional<Long> getCurrentAuditor() {
    return Optional.empty();
    //TODO:selimssevgi: fix here
    // return SecurityUtil.getUserContext()
    //     .map(UserContext::getUserId)
    //     .or(() -> Optional.of(-1L));
  }
}
