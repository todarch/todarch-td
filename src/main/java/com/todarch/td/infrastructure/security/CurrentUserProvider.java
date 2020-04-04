package com.todarch.td.infrastructure.security;

import java.security.Principal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CurrentUserProvider {

  public CurrentUser currentUser() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    Principal principal = attributes.getRequest().getUserPrincipal();
    CurrentUser currentUser = new CurrentUser();
    currentUser.setId(principal.getName());
    return currentUser;
  }
}
