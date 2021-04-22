package ru.task.iss.exception;
/*
 * Date: 4/21/21
 * Time: 3:35 PM
 * */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", ex);
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status, WebRequest request) {
//
//        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//
//        Map<String, Set<String>> errorsMap = fieldErrors.stream().collect(
//                Collectors.groupingBy(FieldError::getField,
//                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet()))
//        );
//
//        return new ResponseEntity<>(errorsMap.isEmpty() ? ex : errorsMap, headers, status);
//    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CustomException ex) {

        HttpStatus httpStatus = ex.getHttpStatus();

        if (httpStatus == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(new Date());
        error.setHttpStatus(httpStatus.value());
        error.setError("Something went wrong");
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }
}
