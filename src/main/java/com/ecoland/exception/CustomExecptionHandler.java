package com.ecoland.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecoland.common.Constants;
import com.ecoland.model.ErrorResponse;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@ControllerAdvice
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CustomExecptionHandler extends ResponseEntityExceptionHandler {

    private Map<String, String> errors = new HashMap<>();

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
	errors.put(Constants.RECORD_NOT_FOUND, ex.getLocalizedMessage());
	ErrorResponse error = new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.RECORD_NOT_FOUND,
		errors);
	return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	    HttpHeaders headers, HttpStatus status, WebRequest request) {
	for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	    errors.put(error.getField(), error.getDefaultMessage());
	}
	ErrorResponse error = new ErrorResponse(Constants.HTTP_CODE_400, new Date(), ex.getLocalizedMessage(), errors);
	return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
	ErrorResponse error = new ErrorResponse(Constants.HTTP_CODE_500, new Date(), ex.getLocalizedMessage(), errors);
	return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
