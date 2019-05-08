package com.newsdoo.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.newsdoo.seckill.entity.Seckill;

public interface SeckillDao {

	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime 对应create_time
	 * @return 查询影响行数
	 */
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	/**
	 * 根据ID来查询秒杀对象
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * 根据偏移量查询秒杀商品列表
	 * @param offset
	 * @param limit
	 * @return
	 */
//	添加mybatis提供的Param注释
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
	
	/**
	 * 使用存储过程执行秒杀
	 * @param paramMap
	 */
	void killByProcedure(Map<String, Object> paramMap);
}
