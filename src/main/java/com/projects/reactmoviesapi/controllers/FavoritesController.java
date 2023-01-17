package com.projects.reactmoviesapi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.reactmoviesapi.dtos.FavoriteDTO;
import com.projects.reactmoviesapi.dtos.ShowDTO;
import com.projects.reactmoviesapi.services.FavoritesService;

@RestController
@RequestMapping("favorites")
public class FavoritesController {

  @Autowired
  private FavoritesService favoritesService;

  @GetMapping
  public ResponseEntity<List<ShowDTO>> getFavorites(@AuthenticationPrincipal(expression = "id") long userId) {
    return ResponseEntity.ok(this.favoritesService.getFavoriteMovies(userId));
  }

  @GetMapping("/check/{id}")
  public ResponseEntity<Boolean> checkIfFavorite(@PathVariable("id") long movieId,
      @AuthenticationPrincipal(expression = "id") long userId) {
    return ResponseEntity.ok(this.favoritesService.isFavorite(movieId, userId));
  }

  @PostMapping
  public ResponseEntity<Void> addFavorite(@Valid @RequestBody FavoriteDTO favoriteMovie,
      @AuthenticationPrincipal(expression = "id") long userId) {
    this.favoritesService.addToFavorites(favoriteMovie.getMovieId(), userId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removeFavorite(@PathVariable("id") long movieId,
      @AuthenticationPrincipal(expression = "id") long userId) {
    this.favoritesService.removeFromFavorites(movieId, userId);
    return ResponseEntity.noContent().build();
  }
}
