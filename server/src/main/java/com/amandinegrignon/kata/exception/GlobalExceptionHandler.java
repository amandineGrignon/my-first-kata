package com.amandinegrignon.kata.exception;

import com.amandinegrignon.kata.controller.MovieController;
import com.amandinegrignon.kata.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    /** Provides handling for exceptions throughout this service. */
    @ExceptionHandler({ ResourceNotFoundException.class })
    public final ResponseEntity<Void> handleNotFoundException(Exception ex, WebRequest request) {
        LOGGER.error("L'élément est introuvable.", ex);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /** Provides handling for exceptions throughout this service. */
    @ExceptionHandler({ DatabaseException.class })
    public final ResponseEntity<Void> handleDatabaseException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}