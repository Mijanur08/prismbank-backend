package com.wellsfargo.training.prism.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class NoTransactionFoundException extends Exception {

	private static final long serialVersionUID = 3L;
	public NoTransactionFoundException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}

}
