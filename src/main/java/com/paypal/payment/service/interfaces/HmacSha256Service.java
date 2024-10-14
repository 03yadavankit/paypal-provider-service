package com.paypal.payment.service.interfaces;

public interface HmacSha256Service {
    String calculateHMAC(String data);
    boolean verifyHMAC(String data, String receivedHmac);
}
