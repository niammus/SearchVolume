package com.sallics.search.volume.ErrorHandling;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * APiError class
 *
 * This class is used to return a complete customised error message from the controller whenever Error/Exception
 * is thrown during the process
 *
 * @author mustafa
 */
@Builder
@AllArgsConstructor
@Data
public class ApiError {

    /**
     * the relevant Http Status of the returned Reponse
     */
    private HttpStatus status;

    /**
     * current timestamp
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    /**
     * error message
     */
    private String message;

    /**
     * the name of the thrown Exception
     */
    private String exception;

    private ApiError() {

    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.exception = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, String ex) {
        this();
        this.status = status;
        this.message = message;
        this.exception = ex;
    }
}