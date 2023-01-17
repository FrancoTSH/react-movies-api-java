package com.projects.reactmoviesapi.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDTO {

  @JsonIgnore
  private Long id;
  @NotBlank
  private String name;
  @NotBlank
  @Email
  private String email;
  private String photoUrl;

  @JsonIgnore
  private String password;
}
