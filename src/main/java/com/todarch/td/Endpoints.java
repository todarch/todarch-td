package com.todarch.td;

public final class Endpoints {
  private Endpoints() {
    throw new AssertionError("Cannot create objects from util class");
  }

  public static final String NON_SECURED = "/non-secured";

  private static final String API = "/api";
  public static final String TODOS = API + "/todos";
}
