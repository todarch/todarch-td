package com.todarch.td.infrastructure.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class CurrentUser {
  private String id;
}
