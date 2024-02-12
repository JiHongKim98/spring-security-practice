package com.example.jwt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.dto.AuthRequestDto;
import com.example.jwt.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	// TODO: request DTO 분할
	// TODO: response DTO 추가

	private final AuthService authService;

	@PostMapping("/join")
	public String join(@RequestBody AuthRequestDto authRequestDto) {
		authService.signIn(authRequestDto.getUsername(), authRequestDto.getPassword());
		return "ok";
	}

	@PostMapping("/login")
	public String login(@RequestBody AuthRequestDto authRequestDto) {
		return authService.login(authRequestDto.getUsername(), authRequestDto.getPassword());
	}
}
