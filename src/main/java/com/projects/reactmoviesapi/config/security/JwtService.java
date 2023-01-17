package com.projects.reactmoviesapi.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  @Value("${jwt.expires_in}")
  private Long EXPIRES_IN;

  public String generateToken(String subject) {
    Date expirationDate = new Date(System.currentTimeMillis() + (EXPIRES_IN * 1000));
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(new Date())
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  public String getSubject(String token) {
    Claims decodedToken = this.decode(token);
    return decodedToken.getSubject();
  }

  public boolean validateToken(String token, String subject) {
    Claims decodedToken = this.decode(token);

    Boolean hasExpired = decodedToken.getExpiration().before(new Date());

    return !hasExpired && subject.equals(decodedToken.getSubject());
  }

  public Claims decode(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token).getBody();
  }
}
