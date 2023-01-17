package com.projects.reactmoviesapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projects.reactmoviesapi.entities.Movie;
import com.projects.reactmoviesapi.entities.MovieType;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {
  public List<Movie> findByType(MovieType type);

  @Query(value = "SELECT * FROM movies WHERE type = ?1 ORDER BY RANDOM() LIMIT ?2", nativeQuery = true)
  public List<Movie> getRandomMovies(String type, int limit);
}
