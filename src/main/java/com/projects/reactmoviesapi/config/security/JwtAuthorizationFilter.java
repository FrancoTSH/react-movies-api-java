package com.projects.reactmoviesapi.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.projects.reactmoviesapi.entities.User;
import com.projects.reactmoviesapi.services.UserService;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private static final String HEADER_TOKEN_PREFIX = "Bearer ";
  private static final String AUTH_HEADER = "Authorization";

  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getToken(request);
    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    String username = jwtService.getSubject(token);
    User user = userService.loadUserByUsername(username);
    if (jwtService.validateToken(token, user.getUsername())) {
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
          user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    String authHeader = request.getHeader(AUTH_HEADER);
    if (authHeader != null && authHeader.startsWith(HEADER_TOKEN_PREFIX)) {
      return authHeader.substring(7);
    }
    return null;
  }

}
