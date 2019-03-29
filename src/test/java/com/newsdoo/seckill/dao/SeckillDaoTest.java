package com.newsdoo.seckill.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.newsdoo.seckill.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDaoTest {
	
	@Resource
	private SeckillDao seckkillDao;
	
	@Test
	public void testQueryById() throws Exception{
		long id = 1000;
		Seckill seckill = seckkillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	
	@Test
	public void testQueryAll() throws Exception{
		/*
		 * Caused by: org.apache.ibatis.binding.BindingException: 
		 * Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
		 * List<Seckill> queryAll(int offset,int limit)
		 * java没有保存形参的记录：queryAll(int offset,int limit)->queryAll(arg0,arg1)
		 */
		List<Seckill> seckills = seckkillDao.queryAll(0, 100);
		for (Seckill seckill : seckills) {
			System.out.println(seckill);
		}
	}
	
	@Test
	public void testReduceNumber() throws Exception{
		Date killTime = new Date();
		int updateCount = seckkillDao.reduceNumber(1000L, killTime);
		System.out.println("updateCount:" + updateCount);
	}
	
}
