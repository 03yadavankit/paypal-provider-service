package com.paypal.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.paypal.payment.constant.ErrorCodeEnum;
import com.paypal.payment.pojo.ErrorResponse;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        System.out.println("Validation exception handled | errorResponse:" + errorResponse);
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
        		ErrorCodeEnum.GENERIC_ERROR.getErrorCode(),
				 ErrorCodeEnum.GENERIC_ERROR.getErrorMessage());
        System.out.println("Validation exception handled | errorResponse:" + errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
