package com.sallics.search.volume.ErrorHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


/**
 * intercept any Exception thrown by any controller/service and handle it depends on the handler function
 *
 * can be expanded to handle more exceptions
 *
 * @author mustafa
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ErrorHandling {

    Logger logger = LoggerFactory.getLogger(ErrorHandling.class);

    /**
     * Handles java.lang.Exception.class
     * @param exception
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(java.lang.Exception.class)
    public ResponseEntity<ApiError> exceptionHandler (java.lang.Exception exception) {

        logger.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .exception(exception.getCause().toString())
                .build()
        );
    }




}
