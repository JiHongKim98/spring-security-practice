package com.example.jwt.global.exception.response;

import com.example.jwt.global.exception.code.ErrorCode;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

	private String code;
	private String message;

	public ErrorResponse(ErrorCode errorCode) {
		this.code = errorCode.name();
		this.message = errorCode.getMessage();
	}
}
