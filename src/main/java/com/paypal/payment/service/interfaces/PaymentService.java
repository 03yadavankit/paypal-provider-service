package com.paypal.payment.service.interfaces;

import com.paypal.payment.pojo.CreatePaymentRes;
import com.paypal.payment.pojo.PaymentRequest;

public interface PaymentService {
	
	public CreatePaymentRes createPayment(PaymentRequest paymentRequest);
	
}
