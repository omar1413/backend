package com.omar.errors;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(FileStorageException.class)
	public String handleException(FileStorageException exception) {

		return "dddfff";
	}
}