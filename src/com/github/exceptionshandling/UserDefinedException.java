package com.github.exceptionshandling;

public class UserDefinedException extends Exception {

	private static final long serialVersionUID = -2040806780682598822L;
	public UserDefinedException(String message) {
		super(message);
	}
}
