package com.newsdoo.seckill.exception;

/*
 * √Î…±“µŒÒ“Ï≥£
 */
@SuppressWarnings("serial")
public class SeckillException extends RuntimeException{

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillException(String message) {
		super(message);
	}

}
