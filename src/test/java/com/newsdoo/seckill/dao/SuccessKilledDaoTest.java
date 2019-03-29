package com.newsdoo.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.newsdoo.seckill.entity.Seckill;
import com.newsdoo.seckill.entity.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

	@Resource
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() {
		int insertCount = successKilledDao.insertSuccessKilled(1001L, 13847960080L);
		System.out.println("insertCount: " + insertCount);
	}
	
	@Test
	public void testQueryByIdWithSeckill() {
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1001L,13847960080L);
		Seckill seckill = successKilled.getSeckill();
		System.out.println(successKilled);
		System.out.println(seckill);
	}
	
}
