package com.newsdoo.seckill.dao;

import org.apache.ibatis.annotations.Param;

import com.newsdoo.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {

	/**
	 * ���빺����ϸ���ɹ����ظ�
	 * @param seckillId
	 * @param userPhone
	 * @return ����Ӱ������
	 */
	int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	/**
	 * ����ID��ѯSuccessKilled��Я����ɱ����ʵ��
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
}
