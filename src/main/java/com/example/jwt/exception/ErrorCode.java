package com.example.jwt.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 또는 존재하지 않는 토큰입니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 사용자입니다."),
	DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 username 입니다."),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;

	ErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
		this.code = super.name();
	}
}
