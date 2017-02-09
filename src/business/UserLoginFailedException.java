package business;

import common.NestedException;

public class UserLoginFailedException extends NestedException{

	/**
	 * @param message
	 */
	public UserLoginFailedException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UserLoginFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public UserLoginFailedException(Throwable cause) {
		super(cause);
	}

	
}
