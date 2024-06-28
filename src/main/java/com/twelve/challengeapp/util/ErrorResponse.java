package com.twelve.challengeapp.util;

public class ErrorResponse {
	private int status;
	private String message;

	public ErrorResponse(String message, int status) {
		this.message = message;
		this.status = status;
	}

	// Getters and setters

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}
}
