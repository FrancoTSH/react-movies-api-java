package com.projects.reactmoviesapi.services;

import java.util.List;

import com.projects.reactmoviesapi.dtos.ShowDTO;

public interface IFavoritesService {
  public List<ShowDTO> getFavoriteMovies(long userId);

  public void addToFavorites(long movieId, long userId);

  public void removeFromFavorites(long movieId, long userId);

  public boolean isFavorite(long movieId, long userId);
}
