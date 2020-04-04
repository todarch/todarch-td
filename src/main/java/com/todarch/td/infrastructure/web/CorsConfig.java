package com.todarch.td.infrastructure.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    // config.setAllowCredentials(true);
    //TODO:selimssevgi: don't do this in production, use a proper list  of allowed origins
    config.addAllowedOrigin(CorsConfiguration.ALL);
    config.addAllowedHeader(CorsConfiguration.ALL);
    config.addAllowedMethod(CorsConfiguration.ALL);
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
