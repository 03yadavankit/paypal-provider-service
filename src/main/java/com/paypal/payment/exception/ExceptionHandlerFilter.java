package com.paypal.payment.exception;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.paypal.payment.constant.ErrorCodeEnum;
import com.paypal.payment.pojo.ErrorResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            System.out.println("ExceptionHandlerFilter Before doFilter");
            filterChain.doFilter(request, response);
            System.out.println("ExceptionHandlerFilter After doFilter");
        } catch (ValidationException ex) {
            System.out.println("Validation exception is -> " + ex.getErrorMessage());
            ErrorResponse paymentResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
            System.out.println("PaymentResponse is -> " + paymentResponse);
            Gson gson = new Gson();
            response.setStatus(ex.getHttpStatus().value());
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(paymentResponse));
            response.getWriter().flush();
        } catch (Exception ex) {
        	System.out.println("Generic exception message is -> " + ex.getMessage());
            ErrorResponse paymentResponse = new ErrorResponse(ErrorCodeEnum.GENERIC_ERROR.getErrorCode(), ErrorCodeEnum.GENERIC_ERROR.getErrorMessage());
            
            System.out.println("paymentResponse : "+paymentResponse);
            
            Gson gson = new Gson();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(paymentResponse));
            
        }
    }
}

