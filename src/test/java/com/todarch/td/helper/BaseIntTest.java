package com.todarch.td.helper;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseIntTest {
  @Autowired
  protected DbHelper dbHelper;

  @AfterEach
  public void tearDown() {
    dbHelper.clearAll();
  }
}
