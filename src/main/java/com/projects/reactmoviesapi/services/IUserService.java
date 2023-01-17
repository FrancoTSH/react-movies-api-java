package com.projects.reactmoviesapi.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.projects.reactmoviesapi.dtos.UserDTO;

public interface IUserService extends UserDetailsService {
  public UserDTO findById(long id);

  public UserDTO save(UserDTO user);

  public UserDTO update(long id, UserDTO user);
}
