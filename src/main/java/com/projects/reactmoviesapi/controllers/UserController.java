package com.projects.reactmoviesapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.reactmoviesapi.dtos.UserDTO;
import com.projects.reactmoviesapi.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("profile")
  public ResponseEntity<UserDTO> profile(@AuthenticationPrincipal(expression = "id") long userId) {
    return ResponseEntity.ok(this.userService.findById(userId));
  }

  @PutMapping("profile")
  public ResponseEntity<UserDTO> updateProfile(@Valid @RequestBody UserDTO user,
      @AuthenticationPrincipal(expression = "id") long userId) {
    return ResponseEntity.ok(this.userService.update(userId, user));
  }
}
