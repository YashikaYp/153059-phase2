package com.cg.mypaymentapp.exception;

public class InvalidInputException extends RuntimeException {

	public InvalidInputException(String msg) {
		System.err.println(msg);
	}
}
