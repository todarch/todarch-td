package com.todarch.td.helper;

import java.util.UUID;

public final class TestUser {
  public TestUser() {
    throw new AssertionError("No instace of utility class");
  }

  public static final String EMAIL = "test2@user.com";
  public static final String ID = UUID.randomUUID().toString();
  public static final String ANOTHER_USER_ID = UUID.randomUUID().toString();

  public static final String PREFIXED_TOKEN = null;
}
