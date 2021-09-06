package com.rg.expection;

import org.springframework.security.core.AuthenticationException;

public class CaptchaException extends AuthenticationException{

	public CaptchaException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}



}
