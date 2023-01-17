package com.projects.reactmoviesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.reactmoviesapi.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
  public User findByEmail(String email);
}
