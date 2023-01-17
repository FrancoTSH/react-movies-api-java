package com.projects.reactmoviesapi.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.reactmoviesapi.config.security.JwtService;
import com.projects.reactmoviesapi.dtos.AuthenticationTokensDTO;
import com.projects.reactmoviesapi.dtos.LoginDTO;
import com.projects.reactmoviesapi.dtos.RegisterDTO;
import com.projects.reactmoviesapi.dtos.UserDTO;
import com.projects.reactmoviesapi.entities.User;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

  private JwtService jwtService;
  private AuthenticationManager authenticationManager;
  private UserService userService;
  private PasswordEncoder passwordEncoder;

  private ModelMapper mapper;

  public AuthenticationTokensDTO login(LoginDTO credentials) throws BadCredentialsException {
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(credentials.getEmail(),
        credentials.getPassword());
    authenticationManager.authenticate(authentication);
    User user = userService.loadUserByUsername(credentials.getEmail());

    AuthenticationTokensDTO tokens = this.generateTokens(user.getUsername());
    user.setRefreshToken(passwordEncoder.encode(tokens.getRefreshToken()));
    return tokens;
  }

  public AuthenticationTokensDTO register(RegisterDTO user) {
    String hashedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(hashedPassword);
    UserDTO savedUser = userService.save(mapper.map(user, UserDTO.class));
    AuthenticationTokensDTO tokens = this.generateTokens(savedUser.getEmail());
    userService.updateRefreshToken(savedUser.getId(), passwordEncoder.encode(tokens.getRefreshToken()));
    return tokens;
  }

  private AuthenticationTokensDTO generateTokens(String subject) {
    String accessToken = jwtService.generateToken(subject);
    String refreshToken = jwtService.generateToken(subject);
    return new AuthenticationTokensDTO(accessToken, refreshToken);
  }

  public AuthenticationTokensDTO createAccessTokenFromRefreshToken(String refreshToken) {
    String subject = jwtService.decode(refreshToken).getSubject();
    User user = userService.loadUserByUsername(subject);
    Boolean isRefreshTokenMatching = passwordEncoder.matches(refreshToken, user.getRefreshToken());
    Boolean isTokenValid = jwtService.validateToken(refreshToken, subject);
    if (!isTokenValid || !isRefreshTokenMatching) {
      throw new JwtException("Invalid token");
    }
    return this.generateTokens(user.getUsername());
  }
}
