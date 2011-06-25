package com.icepack.MeetUp1.common;

/**
 * Serverside Error Exception
 */
@SuppressWarnings("serial")
public class MUException extends Exception {
	/**
	 * creates exception
	 */
	public MUException() {
	}

	/**
	 * creates exception with Error Message
	 * 
	 * @param message
	 *            Error Message
	 */
	public MUException(String message) {
		super(message);
	}
}
