package com.paypal.payment.service.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.paypal.payment.constant.ErrorCodeEnum;
import com.paypal.payment.exception.ValidationException;
import com.paypal.payment.pojo.CreatePaymentRes;
import com.paypal.payment.pojo.PaymentRequest;


@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
	public CreatePaymentRes createPayment(PaymentRequest paymentRequest) {
		
			if(!paymentRequest.getPayment().getProviderId().equals("PAYPAL")) {
				System.out.println("Provide is invalid");
				
			 throw new ValidationException(
					 ErrorCodeEnum.INVALID_PROVIDER.getErrorCode(),
					 ErrorCodeEnum.INVALID_PROVIDER.getErrorMessage(),
					 HttpStatus.BAD_REQUEST);
                	
			}
			
			System.out.println("Valid request with PAYPAL");
			CreatePaymentRes createPaymentRes = new CreatePaymentRes();
			createPaymentRes.setTxnRef("TxnRef0011");
			createPaymentRes.setProviderRef("ProviderRef0011");
			createPaymentRes.setRedirectUrl("http://redirecturl.com");
		
			System.out.println("createPayment returning response from service " + "response:" + paymentRequest	);
			
		return createPaymentRes;
	}

}
