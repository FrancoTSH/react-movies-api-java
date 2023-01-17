package com.projects.reactmoviesapi.dtos;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationTokensDTO {
  @NotBlank
  private String accessToken;
  @NotBlank
  private String refreshToken;
}
