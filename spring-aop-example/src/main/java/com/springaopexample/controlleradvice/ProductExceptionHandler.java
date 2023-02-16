package com.springaopexample.controlleradvice;

import com.springaopexample.exception.ProductNotFoundException;
import com.springaopexample.response.ErrorCode;
import com.springaopexample.response.ErrorResource;
import com.springaopexample.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResource handler(ProductNotFoundException exception){
        return new ErrorResource(new ErrorResponse(exception.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResource handler(Exception exception){
        return new ErrorResource(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}
