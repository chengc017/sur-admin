package com.sohu.sur.cache.exception;

public class PageCacheException extends Exception {
	/**
	 * Class constructor.
	 */
	public PageCacheException() {
		super();
	}

	/**
	 * Class constructor.
	 * 
	 * @param message
	 *            java.lang.String
	 */
	public PageCacheException(String message) {
		super(message);
	}

	/**
	 * Class constructor.
	 * 
	 * @param message
	 * @param cause
	 */
	public PageCacheException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Class constructor.
	 * 
	 * @param cause
	 */
	public PageCacheException(Throwable cause) {
		super(cause);
	}

}
