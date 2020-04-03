package com.todarch.td.infrastructure.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://stackoverflow.com/questions/57787768/issues-running-example-keycloak-spring-boot-app
 */
@Configuration
public class KeycloakConfig {

  @Bean
  public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }
}

