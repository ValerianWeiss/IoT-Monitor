package com.vuebackend.security;

public class UnauthorizedExecption extends Exception{
    private static final long serialVersionUID = -9120910226562522261L;

	public UnauthorizedExecption(String message){
        super(message);
    }
}