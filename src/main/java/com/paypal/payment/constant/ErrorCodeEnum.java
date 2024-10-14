package com.paypal.payment.constant;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCodeEnum {
	GENERIC_ERROR(10000,"Unable to process request, please try again"),
	INVALID_PROVIDER(10001,"Invalid ProviderID, currently we process only PAYPAL"),
	INVALID_PAYMENT_METHOD(10002,"Invalid Payment Method, currently only APMs are supported"),
	MISSING_HMAC_SIGNATURE(1003,"Missing HMAC signature in header");
	
	private final int errorCode;
	private final String errorMessage;
	
    // Constructor for the enum
	ErrorCodeEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
	
}
