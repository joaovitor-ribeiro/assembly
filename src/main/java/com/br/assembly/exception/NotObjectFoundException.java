package com.br.assembly.exception;

public class NotObjectFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NotObjectFoundException(String message){
		super(message);
	}
	
}
