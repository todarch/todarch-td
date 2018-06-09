package com.todarch.td.helper;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseIntTest {
  @Autowired
  protected DbHelper dbHelper;

  @After
  public void tearDown() {
    dbHelper.clearAll();
  }
}
