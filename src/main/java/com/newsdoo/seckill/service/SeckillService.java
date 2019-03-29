package com.newsdoo.seckill.service;

import java.util.List;

import com.newsdoo.seckill.dto.Exposer;
import com.newsdoo.seckill.dto.SeckillExecution;
import com.newsdoo.seckill.entity.Seckill;
import com.newsdoo.seckill.exception.RepeatKillException;
import com.newsdoo.seckill.exception.SeckillCloseException;
import com.newsdoo.seckill.exception.SeckillException;

/**
 * ҵ���ڣ�վ�ڡ�ʹ���ߡ��Ƕ���� 
 * �������棺�����������ȣ���������������(return����/�쳣)
 */
public interface SeckillService {

	/**
	 * ��ѯ������ɱ��Ʒ
	 * @return
	 */
	List<Seckill> getSeckillList();

	/**
	 * ͨ��Id��ѯ��ɱ��Ʒ
	 * @return
	 */
	Seckill getSeckillById(long seckillId);

	/**
	 * ��ɱ����ʱ�����ɱ�ӿڵ�ַ 
	 * �������ϵͳʱ�����ɱ����ʱ��
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * ִ����ɱ����
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
				throws SeckillException,RepeatKillException,SeckillCloseException;

}
