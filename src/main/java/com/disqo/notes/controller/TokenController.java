package com.disqo.notes.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.disqo.notes.dto.TokenRequest;
import com.disqo.notes.dto.TokenResponse;
import com.disqo.notes.model.User;
import com.disqo.notes.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
public class TokenController {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expirationDateInMs}")
	private long jwtExpirationInMs;

	@Autowired
	UserService userService;

	@PostMapping(value = "/token")
	public ResponseEntity<?> getUserToken(@RequestBody TokenRequest tokenRequest)
			throws Exception {
		User user = userService.getUserByEmailAndPassword(tokenRequest.getEmail(), tokenRequest.getPassword());
		String token = generateToken(user.getEmail());
		TokenResponse tokenReponse = new TokenResponse(user.getEmail(), token, jwtExpirationInMs);
		return ResponseEntity.ok(tokenReponse);
	}

	private String generateToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
