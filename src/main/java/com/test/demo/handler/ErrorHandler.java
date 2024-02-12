package com.test.demo.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.test.demo.exception.DataNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
	
	@Generated
	@ExceptionHandler(SQLServerException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseHandler handler(SQLServerException ex) {
		log.error(ex.getMessage());
		if (emailUsed(ex))
			return ResponseHandler.builder().status(HttpStatus.CONFLICT.value()).description("email is already used").build();
		return ResponseHandler.builder().status(HttpStatus.CONFLICT.value()).title("SQL SERVER").description("SQL SERVER ERROR: Contact your ADMIN").build();
	}
	
	private boolean emailUsed(Exception ex) {
		final String regex = "AK_email?";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(ex.getMessage());
        return matcher.find();
	}
	
	@Generated
	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseHandler handler(ExpiredJwtException ex) {
		log.error(ex.getMessage());
		return ResponseHandler.builder().status(HttpStatus.FORBIDDEN.value()).title("JWT").description("Session expired!!!").build();
	}
	
	@Generated
	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseHandler handler(DataNotFoundException ex) {
		return ResponseHandler.builder().status(HttpStatus.NO_CONTENT.value()).title("Data not found").description(ex.getMessage() + ex.getUuid()).build();
	}
	
}
