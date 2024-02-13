package com.example.jwt.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinRequestDto {

	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
