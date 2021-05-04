package ru.task.iss.exception;
/*
 * Date: 4/21/21
 * Time: 3:35 PM
 * */

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, Set<String>> errorsMap = fieldErrors.stream().collect(
                Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet()))
        );

        return new ResponseEntity<>(errorsMap.isEmpty() ? ex : errorsMap, headers, status);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CustomException ex) {

        HttpStatus httpStatus = ex.getHttpStatus();

        if (httpStatus == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse error = new ErrorResponse();

        if (ex.getError() != null) {
            error.setError(ex.getError());
        } else {
            error.setError("Something went wrong");
        }

        error.setTimestamp(new Date());
        error.setHttpStatus(httpStatus.value());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = MvcException.class)
    public ModelAndView handleException(RuntimeException e) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", e.getMessage());
        mav.setViewName("exception/error");
        return mav;
    }
}
