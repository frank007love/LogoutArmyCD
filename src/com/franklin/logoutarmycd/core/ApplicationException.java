package com.franklin.logoutarmycd.core;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -3753484653094896086L;

	public ApplicationException(String aMsg, Throwable aThrowable){
		super( aMsg, aThrowable);
	}
}
