package com.example.jwt.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum JwtErrorCode {

	UNAUTHORIZED(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰 또는 존재하지 않는 토큰입니다.", "UNAUTHORIZED"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 사용자입니다.", "FORBIDDEN");

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;

	JwtErrorCode(HttpStatus httpStatus, String message, String code) {
		this.httpStatus = httpStatus;
		this.message = message;
		this.code = code;
	}
}
