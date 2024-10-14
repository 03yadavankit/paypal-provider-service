package com.paypal.payment.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRes {
	private String txnRef;
	private String providerRef;
	private String redirectUrl;
	
}
