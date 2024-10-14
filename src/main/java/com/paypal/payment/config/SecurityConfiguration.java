package com.paypal.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import com.paypal.payment.exception.ExceptionHandlerFilter;
import com.paypal.payment.security.HmacFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final HmacFilter hmacFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    public SecurityConfiguration(HmacFilter hmacFilter, ExceptionHandlerFilter exceptionHandlerFilter) {
        this.hmacFilter = hmacFilter;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            .addFilterBefore(exceptionHandlerFilter, AuthorizationFilter.class) // CHANGED: Correct filter to place before
            .addFilterBefore(hmacFilter, AuthorizationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
