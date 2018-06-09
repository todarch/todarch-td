package com.todarch.td.rest.todo;

import com.todarch.security.api.SecurityUtil;
import com.todarch.security.api.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
public class TodoController {

  @GetMapping("/todo")
  public void todo() {
    Optional<UserContext> userContext = SecurityUtil.getUserContext();
    log.info("Did i get user context correct?");
  }
}
