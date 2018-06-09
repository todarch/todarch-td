package com.todarch.td;

public final class Endpoints {
  private Endpoints() {
    throw new AssertionError("Cannot create objects from util class");
  }

  public static final String NON_SECURED = "/non-secured";
  public static final String UP = NON_SECURED + "/up";

  private static final String API = "/api";
}
