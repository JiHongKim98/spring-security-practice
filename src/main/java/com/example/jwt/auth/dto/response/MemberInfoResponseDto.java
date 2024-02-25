package com.example.jwt.auth.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponseDto {

	private String username;
	private List<String> roles;

	public static MemberInfoResponseDto from(String username, List<String> roles) {
		return MemberInfoResponseDto
			.builder()
			.username(username)
			.roles(roles)
			.build();
	}
}
