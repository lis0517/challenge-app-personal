package com.twelve.challengeapp.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.twelve.challengeapp.util.ErrorResponse;
import com.twelve.challengeapp.util.ErrorResponseFactory;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(Exception ex) {
		return ErrorResponseFactory.notFound(ex.getMessage());
	}

	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTokenNotFoundException(TokenNotFoundException ex) {
		return ErrorResponseFactory.notFound(ex.getMessage());
	}

	@ExceptionHandler(DuplicateUsernameException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateUsernameException(DuplicateUsernameException ex) {
		return  ErrorResponseFactory.conflict(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String errorMessage = error.getDefaultMessage();
			errors.add(errorMessage);
		});

		String message = String.join(", ", errors);
		return ErrorResponseFactory.badRequest(message);
	}

	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ErrorResponse> handlePasswordMismatchException(PasswordMismatchException ex) {
		return ErrorResponseFactory.unauthorized(ex.getMessage());
	}

	@ExceptionHandler(UsernameMismatchException.class)
	public ResponseEntity<ErrorResponse> handleUsernameMismatchException(UsernameMismatchException ex) {
		return ErrorResponseFactory.unauthorized(ex.getMessage());
	}

	@ExceptionHandler(UserWithdrawalException.class)
	public ResponseEntity<ErrorResponse> handleUserWithdrawalException(UserWithdrawalException ex) {
		return ErrorResponseFactory.unauthorized(ex.getMessage());
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
		return ErrorResponseFactory.notFound(ex.getMessage());
	}

	@ExceptionHandler(AlreadyAdminException.class)
	public ResponseEntity<ErrorResponse> handleAlreadyAdminException(AlreadyAdminException ex) {
		return ErrorResponseFactory.badRequest(ex.getMessage());
	}

	@ExceptionHandler(UserDeletedException.class)
	public ResponseEntity<ErrorResponse> handleUserDeletedException(UserDeletedException ex) {
		return ErrorResponseFactory.badRequest(ex.getMessage());
	}

	@ExceptionHandler(CommentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException ex) {
		return ErrorResponseFactory.notFound(ex.getMessage());
	}

	@ExceptionHandler(SelfCommentException.class)
	public ResponseEntity<ErrorResponse> handleSelfCommentException(SelfCommentException ex) {
		return ErrorResponseFactory.badRequest(ex.getMessage());
	}
}
