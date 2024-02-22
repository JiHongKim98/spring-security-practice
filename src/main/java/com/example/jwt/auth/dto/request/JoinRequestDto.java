package com.example.jwt.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequestDto {

	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
