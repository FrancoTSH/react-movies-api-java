package com.projects.reactmoviesapi.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.reactmoviesapi.dtos.AuthenticationTokensDTO;
import com.projects.reactmoviesapi.dtos.LoginDTO;
import com.projects.reactmoviesapi.dtos.RegisterDTO;
import com.projects.reactmoviesapi.services.AuthService;
import com.projects.reactmoviesapi.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

  private AuthService authService;
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationTokensDTO> login(@Valid @RequestBody LoginDTO credentials) {
    return ResponseEntity.ok(authService.login(credentials));
  }

  @PostMapping("/register")
  public ResponseEntity<AuthenticationTokensDTO> register(@Valid @RequestBody RegisterDTO registerData) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerData));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@AuthenticationPrincipal(expression = "id") long userId) {
    userService.updateRefreshToken(userId, null);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/refresh-tokens")
  public ResponseEntity<AuthenticationTokensDTO> refreshToken(@Valid @RequestBody AuthenticationTokensDTO tokens) {
    return ResponseEntity.ok(authService.createAccessTokenFromRefreshToken(tokens.getRefreshToken()));
  }
}
