package com.disqo.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
	private String email;
	private String token;
	private Long expiration;
}
