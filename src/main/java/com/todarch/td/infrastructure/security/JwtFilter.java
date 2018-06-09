package com.todarch.td.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filters incoming requests and installs a Spring Security principal
 * if a header corresponding to a valid user is found.
 */
public class JwtFilter extends GenericFilterBean {

  private JwtTokenUtil jwtTokenUtil;

  public JwtFilter(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse,
                       FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    String jwt = resolveToken(httpServletRequest);

    if (StringUtils.hasText(jwt) && this.jwtTokenUtil.validateToken(jwt)) {
      Authentication authentication = this.jwtTokenUtil.getAuthentication(jwt);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(JwtTokenUtil.AUTH_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenUtil.AUTH_PREFIX)) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }
}

