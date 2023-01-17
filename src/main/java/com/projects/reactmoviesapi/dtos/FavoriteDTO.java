package com.projects.reactmoviesapi.dtos;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FavoriteDTO {
  @NotNull
  private Long movieId;
}
