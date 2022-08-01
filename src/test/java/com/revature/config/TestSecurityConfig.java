package com.revature.config;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@TestConfiguration
public class TestSecurityConfig {

	static final String AUTH0_TOKEN = "token";
	static final String SUB = "sub";
	static final String AUTH0_ID = "sms|12345678";

	@Bean
	public JwtDecoder jwtDecoder() {
		return new JwtDecoder() {

			@Override
			public Jwt decode(String token) {
				return jwt();
			}

		};
	}

	public Jwt jwt() {
		Map<String, Object> claims = new HashMap<>();
		claims.put(SUB, AUTH0_ID);
		
		Map<String, Object> headers = new HashMap<>();
		headers.put("alg", "none");

		return new Jwt(AUTH0_TOKEN, Instant.now(), Instant.now().plusSeconds(30), headers, claims);
	}
}
