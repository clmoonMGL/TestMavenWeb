package com.newsdoo.seckill.exception;

/**
 * �ظ���ɱ�쳣���������쳣��
 */
@SuppressWarnings("serial")
public class RepeatKillException extends SeckillException{

	public RepeatKillException(String message) {
        super(message);
    }

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
