package com.projects.reactmoviesapi.services;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projects.reactmoviesapi.dtos.UserDTO;
import com.projects.reactmoviesapi.entities.User;
import com.projects.reactmoviesapi.repositories.IUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

  private IUserRepository userRepository;

  private ModelMapper mapper;

  @Override
  public User loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.userRepository.findByEmail(email);
  }

  @Override
  public UserDTO findById(long id) {
    User user = this.userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    return this.mapper.map(user, UserDTO.class);
  }

  @Override
  public UserDTO save(UserDTO user) {
    User userEntity = this.mapper.map(user, User.class);
    return this.mapper.map(this.userRepository.save(userEntity), UserDTO.class);
  }

  @Override
  public UserDTO update(long id, UserDTO user) {
    User userEntity = this.userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    this.mapper.map(user, userEntity);
    return this.mapper.map(this.userRepository.save(userEntity), UserDTO.class);
  }

  public void updateRefreshToken(long id, String refreshToken) {
    User user = this.userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    user.setRefreshToken(refreshToken);
    userRepository.save(user);
  }
}
