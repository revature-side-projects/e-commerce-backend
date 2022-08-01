package com.revature.config;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@TestConfiguration
public class TestConfig {

  static final String AUTH0_TOKEN = "token";
  static final String SUB = "sub";
  static final String AUTH0_ID = "sms|12345678";

  @Bean
  public JwtDecoder jwtDecoder() {
    // This anonymous class needs for the possibility of using SpyBean in test methods
    // Lambda cannot be a spy with spring @SpyBean annotation
    return new JwtDecoder() {
      @Override
      public Jwt decode(String token) {
        return jwt();
      }
    };
  }

  public Jwt jwt() {

    // This is a place to add general and maybe custom claims which should be available after parsing token in the live system
    Map<String, Object> claims = new HashMap<>();		
    claims.put(SUB, AUTH0_ID);
    
    Map<String, Object> headers = new HashMap<>();
	headers.put("alg", "none");


    //This is an object that represents contents of jwt token after parsing
    return new Jwt(
        AUTH0_TOKEN,
        Instant.now(),
        Instant.now().plusSeconds(30),
        headers,
        claims
    );
  }

}