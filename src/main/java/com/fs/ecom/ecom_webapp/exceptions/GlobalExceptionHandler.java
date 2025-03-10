package com.fs.ecom.ecom_webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleException(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),errors);
    }

    @ExceptionHandler(InvalidProductException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleException(InvalidProductException e) {
        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(),errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleException(UserNotFoundException e) {
        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),errors);
    }

    @ExceptionHandler(InvalidDiscountException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleException(InvalidDiscountException e) {
        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(),errors);
    }

}
