package com.projects.reactmoviesapi.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class MovieDetailsDTO {
  private Long id;
  private String title;
  private String description;
  private Date releaseDate;
  private String genre;
  private int runtime;
  private String backdropImg;
  private String posterImg;
}
