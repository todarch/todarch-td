package com.todarch.td.infrastructure.persistence;

import com.todarch.security.api.SecurityUtil;
import com.todarch.security.api.UserContext;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {
  @Override
  public Optional<Long> getCurrentAuditor() {
    return SecurityUtil.getUserContext()
        .map(UserContext::getUserId);
  }
}
