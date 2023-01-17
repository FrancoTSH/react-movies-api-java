package com.projects.reactmoviesapi.services;

import java.util.List;
import java.util.Optional;

import com.projects.reactmoviesapi.dtos.MovieDetailsDTO;
import com.projects.reactmoviesapi.dtos.SeriesDetailsDTO;
import com.projects.reactmoviesapi.dtos.ShowDTO;

public interface IMovieService {
  public List<ShowDTO> getMovies(Optional<String> name, Optional<String> genre, Optional<String> orderBy,
      Optional<String> sortBy);

  public List<ShowDTO> getSeries(Optional<String> name, Optional<String> genre, Optional<String> orderBy,
      Optional<String> sortBy);

  public MovieDetailsDTO findMovieById(long id);

  public SeriesDetailsDTO findSeriesById(long id);

  public MovieDetailsDTO getRandomMovie();

  public SeriesDetailsDTO getRandomSeries();

  public List<ShowDTO> getPopularMovies();

  public List<ShowDTO> getPopularSeries();
}
