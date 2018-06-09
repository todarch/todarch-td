package com.todarch.td.infrastructure.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private JwtTokenUtil jwtTokenUtil;

  public JwtConfigurer(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    JwtFilter customFilter = new JwtFilter(jwtTokenUtil);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
