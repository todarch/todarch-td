package com.todarch.td.rest;

import com.todarch.td.Endpoints;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping(Endpoints.UP)
  public String up() {
    return "I am Todarch Todo Service, up and running";
  }
}
