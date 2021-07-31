package com.franklin.logoutarmycd.core.storage;

public class StorageException extends Exception {

	private static final long serialVersionUID = 6687155014425117219L;

	public StorageException(String aMsg, Throwable aThrowable){
		super(aMsg, aThrowable);
	}
	
	public StorageException(String aMsg){
		super(aMsg);
	}
}
