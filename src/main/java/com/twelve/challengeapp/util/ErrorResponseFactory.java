package com.twelve.challengeapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponseFactory {

	public static ResponseEntity<ErrorResponse> notFound(String message) {
		ErrorResponse response = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<ErrorResponse> badRequest(String message) {
		ErrorResponse response = new ErrorResponse(message, HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<ErrorResponse> unauthorized(String message) {
		ErrorResponse response = new ErrorResponse(message, HttpStatus.UNAUTHORIZED.value());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	public static ResponseEntity<ErrorResponse> conflict(String message) {
		ErrorResponse response = new ErrorResponse(message, HttpStatus.CONFLICT.value());
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
}
