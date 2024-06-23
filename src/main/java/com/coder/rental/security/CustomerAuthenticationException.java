package com.coder.rental.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Barry
 * @project auto_rental
 * @date 17/6/2024
 */
public class CustomerAuthenticationException extends AuthenticationException {
	public CustomerAuthenticationException( String msg ) {
		super(msg);
	}
}
