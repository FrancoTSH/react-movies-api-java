package com.projects.reactmoviesapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import com.projects.reactmoviesapi.dtos.MovieDetailsDTO;
import com.projects.reactmoviesapi.dtos.SeriesDetailsDTO;
import com.projects.reactmoviesapi.dtos.ShowDTO;
import com.projects.reactmoviesapi.entities.Movie;
import com.projects.reactmoviesapi.entities.MovieType;
import com.projects.reactmoviesapi.repositories.IMovieRepository;

@Service
public class MovieService implements IMovieService {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private IMovieRepository movieRepository;

  @Autowired
  private ModelMapper mapper;

  @Override
  public List<ShowDTO> getMovies(Optional<String> title, Optional<String> genre, Optional<String> orderBy,
      Optional<String> sortBy) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Movie> criteria = cb.createQuery(Movie.class);
    Root<Movie> root = criteria.from(Movie.class);
    criteria.where(cb.equal(root.get("type"), MovieType.movie));
    if (title.isPresent()) {
      criteria.where(cb.like(root.get("title"), "%" + title.get() + "%"));
    }

    if (genre.isPresent()) {
      criteria.where(cb.equal(root.get("genre"), genre.get()));
    }

    if (orderBy.isPresent() && sortBy.isPresent()) {
      criteria.orderBy(toOrders(Sort.by(sortBy.get().toUpperCase(), orderBy.get()), root, cb));
    }
    return em.createQuery(criteria).getResultList().stream().map(movie -> mapper.map(movie, ShowDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<ShowDTO> getSeries(Optional<String> title, Optional<String> genre, Optional<String> orderBy,
      Optional<String> sortBy) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Movie> criteria = cb.createQuery(Movie.class);
    Root<Movie> root = criteria.from(Movie.class);
    criteria.where(cb.equal(root.get("type"), MovieType.series));
    if (title.isPresent()) {
      criteria.where(cb.like(root.get("title"), "%" + title.get() + "%"));
    }

    if (genre.isPresent()) {
      criteria.where(cb.equal(root.get("genre"), genre.get()));
    }

    if (orderBy.isPresent() && sortBy.isPresent()) {
      criteria.orderBy(toOrders(Sort.by(sortBy.get().toUpperCase(), orderBy.get()), root, cb));
    }
    return em.createQuery(criteria).getResultList().stream().map(movie -> mapper.map(movie, ShowDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public MovieDetailsDTO findMovieById(long id) {
    Movie movie = this.movieRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    return this.mapper.map(movie, MovieDetailsDTO.class);
  }

  @Override
  public SeriesDetailsDTO findSeriesById(long id) {
    Movie movie = this.movieRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    return this.mapper.map(movie, SeriesDetailsDTO.class);
  }

  @Override
  public MovieDetailsDTO getRandomMovie() {
    Movie movie = this.movieRepository.getRandomMovies(MovieType.movie.toString(), 1).get(0);
    return this.mapper.map(movie, MovieDetailsDTO.class);
  }

  @Override
  public SeriesDetailsDTO getRandomSeries() {
    Movie movie = this.movieRepository.getRandomMovies(MovieType.series.toString(), 1).get(0);
    return this.mapper.map(movie, SeriesDetailsDTO.class);
  }

  @Override
  public List<ShowDTO> getPopularMovies() {
    List<Movie> movies = this.movieRepository.getRandomMovies(MovieType.movie.toString(), 10);
    return movies.stream().map(movie -> mapper.map(movie, ShowDTO.class)).collect(Collectors.toList());
  }

  @Override
  public List<ShowDTO> getPopularSeries() {
    List<Movie> movies = this.movieRepository.getRandomMovies(MovieType.series.toString(), 10);
    return movies.stream().map(movie -> mapper.map(movie, ShowDTO.class)).collect(Collectors.toList());
  }
}
