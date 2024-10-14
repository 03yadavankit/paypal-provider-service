package com.paypal.payment.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.paypal.payment.constant.ErrorCodeEnum;
import com.paypal.payment.exception.ValidationException;
import com.paypal.payment.service.interfaces.HmacSha256Service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class HmacFilter extends OncePerRequestFilter {
	
	
	private HmacSha256Service hmacSha256Service;
	private Gson gson;
	
    public HmacFilter(HmacSha256Service hmacSha256Service , Gson gson ) {
        this.hmacSha256Service = hmacSha256Service;
        this.gson = gson;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    System.out.println("==================== HmacFilter: doFilterInternal ====================");
	    
	    
	    String receivedHmacSignature = request.getHeader("HmacSignature");
	    
	    if(receivedHmacSignature == null) {
	    	
	    	    System.out.println("HmacFilter: Received HMACSignature is null. Sending 401");
	    	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	    
	    	    throw new ValidationException(
						 ErrorCodeEnum.MISSING_HMAC_SIGNATURE.getErrorCode(),
						 ErrorCodeEnum.MISSING_HMAC_SIGNATURE.getErrorMessage(),
						 HttpStatus.BAD_REQUEST);
	        }
	    
	 WrappedRequest wrappedRequest = new WrappedRequest(request);
	    
	    String data = request.getRequestURI();
	    if(wrappedRequest.getBody() != null && !wrappedRequest.getBody().isEmpty()) {
	    	data  = data + "|" + getNormalizedJson(wrappedRequest.getBody());
	    }
	   
	    
	    System.out.println("-----sending request data for processing ----   :  "+data);
	    
	    
	    System.out.println("ReceivedHmacSgnature  : "+ receivedHmacSignature);
	    
	    boolean isValid = hmacSha256Service.verifyHMAC(data, receivedHmacSignature); 
	    
	    if (isValid) {
	        System.out.println("HmacFilter is valid. Calling next filter in line");
	        
	        SecurityContext context = SecurityContextHolder.createEmptyContext();
	        Authentication authentication = new HmacAuthenticationToken("ECOM", "");
	        context.setAuthentication(authentication);
	        SecurityContextHolder.setContext(context);
	        
	        System.out.println("hmacFilter: SecurityContextHolder is set with authenticationtoken " + authentication);
	        
	        filterChain.doFilter(wrappedRequest, response);

	    } else {
	        System.out.println("HmacFilter is NOT valid. Sending 401");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    }
	}
	
	public String getNormalizedJson(String rawJson) {
		JsonElement jsonElement = JsonParser.parseString(rawJson);
		
		return gson.toJson(jsonElement);
	}
	
	

}
