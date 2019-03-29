package com.newsdoo.seckill.service;

import java.util.List;

import com.newsdoo.seckill.dto.Exposer;
import com.newsdoo.seckill.dto.SeckillExecution;
import com.newsdoo.seckill.entity.Seckill;
import com.newsdoo.seckill.exception.RepeatKillException;
import com.newsdoo.seckill.exception.SeckillCloseException;
import com.newsdoo.seckill.exception.SeckillException;

/**
 * 业务层口：站在“使用者”角度设计 
 * 三个方面：方法定义粒度，参数，返回类型(return类型/异常)
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀商品
	 * @return
	 */
	List<Seckill> getSeckillList();

	/**
	 * 通过Id查询秒杀商品
	 * @return
	 */
	Seckill getSeckillById(long seckillId);

	/**
	 * 秒杀开启时输出秒杀接口地址 
	 * 否则输出系统时间和秒杀开启时间
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
				throws SeckillException,RepeatKillException,SeckillCloseException;

}
