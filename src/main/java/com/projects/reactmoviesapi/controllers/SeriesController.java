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

import com.projects.reactmoviesapi.dtos.SeriesDetailsDTO;
import com.projects.reactmoviesapi.dtos.ShowDTO;
import com.projects.reactmoviesapi.services.MovieService;

@RestController
@RequestMapping("series")
public class SeriesController {
  @Autowired
  private MovieService movieService;

  @GetMapping
  public ResponseEntity<List<ShowDTO>> getSeries(@RequestParam("title") Optional<String> title,
      @RequestParam("genre") Optional<String> genre, @RequestParam("sort_by") Optional<String> sortBy,
      @RequestParam("order_by") Optional<String> orderBy) {
    return ResponseEntity.ok(this.movieService.getSeries(title, genre, orderBy, sortBy));
  }

  @GetMapping("/popular")
  public ResponseEntity<List<ShowDTO>> getPopularSeries() {
    return ResponseEntity.ok(this.movieService.getPopularSeries());
  }

  @GetMapping("/random")
  public ResponseEntity<SeriesDetailsDTO> getRandomSeries() {
    return ResponseEntity.ok(this.movieService.getRandomSeries());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SeriesDetailsDTO> getSeries(@PathVariable("id") long id) {
    return ResponseEntity.ok(this.movieService.findSeriesById(id));
  }
}
