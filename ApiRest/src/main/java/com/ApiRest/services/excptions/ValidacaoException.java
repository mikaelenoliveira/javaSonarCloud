package com.ApiRest.services.excptions;

public class ValidacaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ValidacaoException(String msg) {
		super(msg);
	}

}
