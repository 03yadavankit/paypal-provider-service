package com.paypal.payment.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;


public class HmacAuthenticationToken extends AbstractAuthenticationToken {
	
	private static final long serialVersionUID = 4576984321765432546L;
	
	private final Object credential;
	
	private final Object principal;
	
	public HmacAuthenticationToken(Object credential, Object principal) {
		super(null);
        this.credential = credential;
        this.principal = principal;
        setAuthenticated(true);
	}
	

	@Override
    public Object getCredentials() {
        return credential;
    }
	
	@Override
	public Object getPrincipal() {
        return principal;
    }
	
}
