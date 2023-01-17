package com.projects.reactmoviesapi.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginDTO {
  @NotBlank
  @Email
  private String email;
  @NotEmpty
  @Size(min = 6, message = "La contraseña debe tener como minimo 6 caracteres")
  private String password;
}
