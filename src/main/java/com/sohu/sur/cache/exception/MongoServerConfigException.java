package com.sohu.sur.cache.exception;

/**
 * @author chengchengbj8545
 *
 */
public class MongoServerConfigException extends Exception{
	/**
	 * Class constructor.
	 */
	public MongoServerConfigException() {
		super();
	}

	/**
	 * Class constructor.
	 * 
	 * @param message
	 *            java.lang.String
	 */
	public MongoServerConfigException(String message) {
		super(message);
	}

	/**
	 * Class constructor.
	 * 
	 * @param message
	 * @param cause
	 */
	public MongoServerConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Class constructor.
	 * 
	 * @param cause
	 */
	
	public MongoServerConfigException(Throwable cause) {
		super(cause);
	}

}
