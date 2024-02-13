package com.example.jwt.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

	private String accessToken;

	public static LoginResponseDto from(String accessToken) {
		return LoginResponseDto
			.builder()
			.accessToken(accessToken)
			.build();
	}
}
