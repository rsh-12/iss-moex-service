package ru.task.iss.exception;
/*
 * Date: 4/21/21
 * Time: 3:32 PM
 * */

import org.springframework.http.HttpStatus;

public final class CustomException extends RuntimeException {

    private String error;
    private String message;
    private HttpStatus httpStatus;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public CustomException(String error, String message, HttpStatus httpStatus) {
        this.error=error;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
