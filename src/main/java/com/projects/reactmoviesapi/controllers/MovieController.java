package com.projects.reactmoviesapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projects.reactmoviesapi.dtos.MovieDetailsDTO;
import com.projects.reactmoviesapi.dtos.ShowDTO;
import com.projects.reactmoviesapi.services.MovieService;

@RestController
@RequestMapping("movies")
public class MovieController {

  @Autowired
  private MovieService movieService;

  @GetMapping
  public ResponseEntity<List<ShowDTO>> getMovies(@RequestParam("title") Optional<String> title,
      @RequestParam("genre") Optional<String> genre, @RequestParam("sort_by") Optional<String> sortBy,
      @RequestParam("order_by") Optional<String> orderBy) {
    return ResponseEntity.ok(this.movieService.getMovies(title, genre, orderBy, sortBy));
  }

  @GetMapping("/popular")
  public ResponseEntity<List<ShowDTO>> getPopularMovies() {
    return ResponseEntity.ok(this.movieService.getPopularMovies());
  }

  @GetMapping("/random")
  public ResponseEntity<MovieDetailsDTO> getRandomMovie() {
    return ResponseEntity.ok(this.movieService.getRandomMovie());
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovieDetailsDTO> getMovie(@PathVariable("id") long id) {
    return ResponseEntity.ok(this.movieService.findMovieById(id));
  }
}
