package com.springaopexample.exception;

import com.springaopexample.response.ErrorCode;
import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProductNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
