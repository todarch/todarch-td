package com.todarch.td.infrastructure.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtil {

  private SecurityUtil() {
    throw new AssertionError("Cannot create object of utility class");
  }

  /**
   * Get the login of the current user.
   *
   * @return the login of the current user
   */
  public static Optional<String> getCurrentUserLogin() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(authentication -> {
          if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
          } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
          }
          return null;
        });
  }

  /**
   * Gets the login of the current user.
   *
   * @return the login of the current user
   * @throws RuntimeException if not logged-in user found
   */
  public static String tryToGetCurrentUserLogin() {
    return getCurrentUserLogin()
        //TODO:selimssevgi: fix this exception
        .orElseThrow(() -> new RuntimeException("Not logged user found"));
  }

  /**
   * Get the JWT of the current user.
   *
   * @return the JWT of the current user
   */
  public static Optional<String> getCurrentUserJwt() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .filter(authentication -> authentication.getCredentials() instanceof String)
        .map(authentication -> (String) authentication.getCredentials());
  }

  /**
   * Check if a user is authenticated.
   *
   * @return true if the user is authenticated, false otherwise
   */
  public static boolean isAuthenticated() {
    return false;
    // SecurityContext securityContext = SecurityContextHolder.getContext();
    // return Optional.ofNullable(securityContext.getAuthentication())
    //     .map(authentication -> authentication.getAuthorities().stream()
    //         .noneMatch(grantedAuthority ->
    // grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)))
    //     .orElse(false);
  }

  /**
   * If the current user has a specific authority (security role).
   *
   * <p> The name of this method comes from the isUserInRole() method in the Servlet API
   * </p>
   *
   * @param authority the authority to check
   * @return true if the current user has the authority, false otherwise
   */
  public static boolean isCurrentUserInRole(String authority) {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(authentication -> authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
        .orElse(false);
  }
}

