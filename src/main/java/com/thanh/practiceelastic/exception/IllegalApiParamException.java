package com.thanh.practiceelastic.exception;

public class IllegalApiParamException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6616317588579484957L;

	public IllegalApiParamException (String message) {
		super(message);
	}
}
