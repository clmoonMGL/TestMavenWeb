package com.newsdoo.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.newsdoo.seckill.entity.Seckill;

public interface SeckillDao {

	/**
	 * �����
	 * @param seckillId
	 * @param killTime ��Ӧcreate_time
	 * @return ��ѯӰ������
	 */
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	/**
	 * ����ID����ѯ��ɱ����
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * ����ƫ������ѯ��ɱ��Ʒ�б�
	 * @param offset
	 * @param limit
	 * @return
	 */
//	����mybatis�ṩ��Paramע��
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
}