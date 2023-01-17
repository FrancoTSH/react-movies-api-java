package com.projects.reactmoviesapi.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class SeriesDetailsDTO {
  private Long id;
  private String title;
  private String description;
  private Date releaseDate;
  private String genre;
  private String backdropImg;
  private String posterImg;
}
