package com.newsdoo.seckill.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.newsdoo.seckill.dto.Exposer;
import com.newsdoo.seckill.dto.SeckillExecution;
import com.newsdoo.seckill.entity.Seckill;
import com.newsdoo.seckill.exception.RepeatKillException;
import com.newsdoo.seckill.exception.SeckillCloseException;
import com.newsdoo.seckill.service.SeckillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
					   "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list = {}", list);
	}

	@Test
	public void testGetSeckillById() {
		long id = 1000L;
		Seckill seckill = seckillService.getSeckillById(id);
		logger.info("seckill = {}", seckill);
	}

	@Test
	public void testExportSeckillUrl() {
		long id = 1000L;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		logger.info("exposer = {}", exposer);
	}

	@Test
	public void testExecuteSeckill() {
		long seckillId = 1000L;
		long userPhone = 15754313779L;
		String md5 = "27c666b205e1b57bf8d468d8d6b7fc14";
		SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
		logger.info("result = {}", seckillExecution);
	}

	@Test
	public void testSeckillLogic() {
		long seckillId = 1000L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if(exposer.isExposed()) {
			logger.info("exposer = {}", exposer);
			long userPhone = 15344407760L;
			String md5 = exposer.getMd5();
			try {
				SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
				logger.info("result = {}", seckillExecution);
			} catch (RepeatKillException e) {
				logger.error(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			}
		}else {
			logger.warn("exposer = {}", exposer);
		}
	}
	
	@Test
	public void executeSeckillByProcedure() {
		long seckillId = 1003L;
		long userPhone = 15577704440L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if(exposer.isExposed()) {
			String md5 = exposer.getMd5();
			SeckillExecution execution = seckillService.executeSeckillByProcedure(seckillId, userPhone, md5);
			logger.info(execution.getStateInfo());
		}
	}
	
}
