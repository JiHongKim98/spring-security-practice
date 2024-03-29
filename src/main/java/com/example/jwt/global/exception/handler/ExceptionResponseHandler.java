package com.example.jwt.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.jwt.global.exception.DuplicateException;
import com.example.jwt.global.exception.InvalidPasswordException;
import com.example.jwt.global.exception.NotFoundMemberException;
import com.example.jwt.global.exception.code.ErrorCode;
import com.example.jwt.global.exception.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionResponseHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DuplicateException.class)
	public ErrorResponse handleDuplicate(DuplicateException ex) {
		log.info("duplicate username exception", ex);

		return new ErrorResponse(ErrorCode.DUPLICATE_USERNAME);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidPasswordException.class)
	public ErrorResponse handleInvalidPassword(InvalidPasswordException ex) {
		log.info("invalid password exception", ex);

		return new ErrorResponse(ErrorCode.INVALID_PASSWORD);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundMemberException.class)
	public ErrorResponse handleNotFoundMember(NotFoundMemberException ex) {
		log.info("not found member exception");

		return new ErrorResponse(ErrorCode.NOT_FOUND_MEMBER);
	}
}
