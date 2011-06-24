package com.icepack.MeetUp1.common;

/**
 * Serverside Error Exception
 */
@SuppressWarnings("serial")
public class MUPException extends Exception {
	/**
	 * creates exception
	 */
	public MUPException() {
	}

	/**
	 * creates exception with Error Message
	 * 
	 * @param message
	 *            Error Message
	 */
	public MUPException(String message) {
		super(message);
	}
}
