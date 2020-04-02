package com.todarch.td.helper;

// import com.todarch.security.api.JwtUtil;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public final class TestUser {
  public TestUser() {
    throw new AssertionError("No instace of utility class");
  }

  public static final String EMAIL = "test2@user.com";
  public static final Long ID = 5L;

  // private static final JwtUtil jwtUtil = new JwtUtil();
  public static final String PREFIXED_TOKEN = null;

  static {
    // UsernamePasswordAuthenticationToken authenticationToken =
    //     new UsernamePasswordAuthenticationToken(
    //         EMAIL,
    //         "",
    //         List.of(new SimpleGrantedAuthority("ROLE_USER")));
    // String token = jwtUtil.createToken(authenticationToken, true, ID);
    // PREFIXED_TOKEN =  JwtUtil.AUTH_PREFIX + token;
  }
}
