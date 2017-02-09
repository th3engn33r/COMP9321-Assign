package common;

public class ServiceLocatorException extends Exception {

	/**
	 * Constructs an exception with the given message
	 * @param message
	 */
	public ServiceLocatorException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ServiceLocatorException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
