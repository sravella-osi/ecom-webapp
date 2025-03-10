package com.fs.ecom.ecom_webapp.exceptions;

public class CategoryDoesNotExistException extends RuntimeException {
    public CategoryDoesNotExistException(String message) {
        super(message);
    }
}
