package com.example.jwt.exception;

public class InvalidPasswordException extends RuntimeException {

	public InvalidPasswordException(String s) {
		super(s);
	}
}
