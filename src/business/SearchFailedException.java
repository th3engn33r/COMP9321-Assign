package business;

import common.NestedException;

public class SearchFailedException extends NestedException{

	public SearchFailedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	

	/**
	 * @param message
	 * @param cause
	 */
	public SearchFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public SearchFailedException(Throwable cause) {
		super(cause);
	}

	
}
