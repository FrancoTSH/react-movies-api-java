package com.projects.reactmoviesapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.reactmoviesapi.dtos.ShowDTO;
import com.projects.reactmoviesapi.entities.Movie;
import com.projects.reactmoviesapi.entities.User;
import com.projects.reactmoviesapi.repositories.IMovieRepository;
import com.projects.reactmoviesapi.repositories.IUserRepository;

@Service
public class FavoritesService implements IFavoritesService {

  @Autowired
  private IMovieRepository movieRepository;

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private ModelMapper mapper;

  @Override
  public void addToFavorites(long movieId, long userId) {
    User user = this.userRepository.findById(userId).get();
    Optional<Movie> movie = this.movieRepository.findById(movieId);
    user.getFavoriteMovies().add(movie.get());
    this.userRepository.save(user);
  }

  @Override
  public void removeFromFavorites(long movieId, long userId) {
    User user = this.userRepository.findById(userId).get();
    Optional<Movie> movie = this.movieRepository.findById(movieId);
    user.getFavoriteMovies().remove(movie.get());
    this.userRepository.save(user);
  }

  @Override
  public boolean isFavorite(long movieId, long userId) {
    User user = this.userRepository.findById(userId).get();
    return user.getFavoriteMovies().stream().anyMatch(movie -> movie.getId() == movieId);
  }

  @Override
  public List<ShowDTO> getFavoriteMovies(long userId) {
    User user = this.userRepository.findById(userId).get();
    return user.getFavoriteMovies().stream().map(movie -> mapper.map(movie, ShowDTO.class))
        .collect(Collectors.toList());
  }
}
