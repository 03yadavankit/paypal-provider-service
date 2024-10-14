package com.paypal.payment.service.impl;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.paypal.payment.service.interfaces.HmacSha256Service;

@Service
public class HmacSha256ServiceImpl implements HmacSha256Service {
    
    // Logger for better error handling and tracing
    private static final Logger logger = LogManager.getLogger(HmacSha256ServiceImpl.class);
    
    // Ideally, this should be loaded from a config file or environment variable
    private static final String SECRET_KEY = "THIS_IS_MY-SECRET_KEY";

    @Override
    public String calculateHMAC(String jsonInput) {
        try {
            // Generate HMACSHA256 signature
            String signature = generateHMACSHA256Signature(jsonInput, SECRET_KEY);
            System.out.println("Generated HMACSHA256 Signature: " + signature);
            return signature;
        } catch (Exception e) {
            logger.error("Error generating HMACSHA256 signature", e);
        }
        System.out.println("HMACSHA256 failed to Generated  Signature");
        return null;
    }
    
    @Override
    public boolean verifyHMAC(String data, String receivedHmac) {
        try {
            // Calculate the HMAC for the given data
            String calculatedHmac = calculateHMAC(data);
            
            // Securely compare the received HMAC with the calculated one
            if(receivedHmac != null && receivedHmac.equals(calculatedHmac)){
            	System.out.println("Recieved HMACSHA256 Signature: " + receivedHmac);
            	System.out.println("HMACSHA256  Signature is valid");
            	return true;
            }
        } catch (Exception e) {
            logger.error("Error verifying HMAC", e);
        }
        
        System.out.println("HMACSHA256  Signature is invalid");
        return false;
    }
    
    /**
     * Generates an HMACSHA256 signature for the given data using the provided secret key.
     * 
     * @param data      The input string to sign.
     * @param secretKey The secret key for HMACSHA256.
     * @return The generated HMACSHA256 signature encoded in Base64 format.
     * @throws Exception If an error occurs during signature generation.
     */
    public static String generateHMACSHA256Signature(String data, String secretKey) throws Exception {
        // Create a new Mac instance with HMACSHA256 algorithm
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");

        // Initialize the Mac with the secret key
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKeySpec);

        // Compute the HMACSHA256 signature
        byte[] hmacSha256Bytes = sha256Hmac.doFinal(data.getBytes());

        // Convert the byte array to Base64 string
        return Base64.getEncoder().encodeToString(hmacSha256Bytes);
    }
    
}
