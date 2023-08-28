package com.wellsfargo.training.prism.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class PasswordIncorrectException extends Exception{
	
	private static final long serialVersionUID = 2L;
	public PasswordIncorrectException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}

}
