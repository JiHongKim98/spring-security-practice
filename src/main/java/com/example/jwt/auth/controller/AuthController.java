package com.example.jwt.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.auth.dto.request.JoinRequestDto;
import com.example.jwt.auth.dto.request.LoginRequestDto;
import com.example.jwt.auth.dto.response.LoginResponseDto;
import com.example.jwt.auth.dto.response.MemberInfoResponseDto;
import com.example.jwt.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/join")
	@ResponseStatus(HttpStatus.CREATED)
	public void join(@RequestBody @Valid JoinRequestDto joinRequestDto) {
		log.info("Call :: join");
		authService.signIn(joinRequestDto.getUsername(), joinRequestDto.getPassword());
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
		log.info("Call :: login");
		return authService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
	}

	@GetMapping("/me")
	@ResponseStatus(HttpStatus.OK)
	public MemberInfoResponseDto me() {
		log.info("Call :: me");
		return authService.me();
	}
}
