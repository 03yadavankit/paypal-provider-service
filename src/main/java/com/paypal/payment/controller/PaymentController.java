package com.paypal.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.paypal.payment.pojo.CreatePaymentRes;
import com.paypal.payment.pojo.PaymentRequest;
import com.paypal.payment.service.interfaces.HmacSha256Service;
import com.paypal.payment.service.interfaces.PaymentService;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {
	
	private HmacSha256Service hmacSha256Service;
	
	private Gson gson;
	
	private PaymentService paymentService;
	
	public PaymentController(HmacSha256Service hmacSha256Service , Gson gson ,PaymentService paymentService) {
        this.hmacSha256Service = hmacSha256Service;
        this.gson = gson;
        this.paymentService = paymentService;
    }
	
	@PostMapping
    public ResponseEntity<CreatePaymentRes>createPayment(@RequestBody PaymentRequest paymentRequest) {
		System.out.println("Create Payment request recieved  "
                + "paymentRequest : " + paymentRequest);
		
		CreatePaymentRes createPaymentRes = paymentService.createPayment(paymentRequest);
		
		
		
        System.out.println("createPayment received from controller response:" + createPaymentRes);
        
        ResponseEntity<CreatePaymentRes> responseEntity = new ResponseEntity<CreatePaymentRes>(createPaymentRes, HttpStatus.CREATED);
        
        System.out.println("Controller returning responseEntity :" + responseEntity);
        
        return responseEntity;
	}
	
	
	@PostMapping("/{txnRef}/capture")
	public String capturePayment(@PathVariable String txnRef) {
		System.out.println("capturePayment request received | txnRef:" + txnRef);
		
		// TODO handle hmac logic
		
		return "Payment captured:" + txnRef;
	}
	
	@GetMapping("/{txnRef}")
    public String getPayment(@PathVariable String txnRef) {
		System.out.println("getPayment request received | txnRef:" + txnRef);
		
		// TODO handle hmac logic
		
		return "getPayment:" + txnRef;
    }
	
	
}
