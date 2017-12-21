package com.gamejelly.gong2.utils;

/**
 * @author bezy 2013-12-6
 * 
 */
public class GongRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GongRuntimeException() {
	}

	public GongRuntimeException(String message) {
		super(message);
	}

	public GongRuntimeException(Throwable cause) {
		super(cause);
	}

	public GongRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
