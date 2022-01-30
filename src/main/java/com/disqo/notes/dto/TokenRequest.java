package com.disqo.notes.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {
	@NotEmpty(message = "email cannot be null")
	private String email;
	@NotEmpty(message = "password cannot be null")
	private String password;
}
