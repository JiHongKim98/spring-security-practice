package com.example.jwt.dto;

import lombok.Data;

@Data
public class AuthRequestDto {

	private String username;

	private String password;
}
