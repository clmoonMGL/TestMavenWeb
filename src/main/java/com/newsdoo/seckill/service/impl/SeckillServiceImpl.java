package com.newsdoo.seckill.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.newsdoo.seckill.dao.SeckillDao;
import com.newsdoo.seckill.dao.SuccessKilledDao;
import com.newsdoo.seckill.dao.cache.RedisDao;
import com.newsdoo.seckill.dto.Exposer;
import com.newsdoo.seckill.dto.SeckillExecution;
import com.newsdoo.seckill.entity.Seckill;
import com.newsdoo.seckill.entity.SuccessKilled;
import com.newsdoo.seckill.enums.SeckillStateEnum;
import com.newsdoo.seckill.exception.RepeatKillException;
import com.newsdoo.seckill.exception.SeckillCloseException;
import com.newsdoo.seckill.exception.SeckillException;
import com.newsdoo.seckill.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource//@Autowired
	private SeckillDao seckillDao;
	
	@Resource//@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Autowired
	private RedisDao redisDao;
	
	//md5盐值字符串，用于混淆MD5
	private final String slat="asd2f5&%*%16845613asdafghah=_:}:";
	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getSeckillById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		//优化点：缓存优化 在超时的基础上维护一致性
		//1.访问redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill == null) {
			//2.访问数据库
			seckill = this.getSeckillById(seckillId);
			if(seckill == null) {
				return new Exposer(false, seckillId);
			}else {
				redisDao.putSeckill(seckill);
			}
		}
		//优化前↓↓↓
//		Seckill seckill = this.getSeckillById(seckillId);
//		if(seckill == null) {
//			return new Exposer(false, seckillId);
//		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if(nowTime.getTime() < startTime.getTime()||
				nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}
	
	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		
		if(md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite.");
		}
		try {
			//执行秒杀逻辑：减库存、购买记录
			Date nowTime = new Date();
			//优化前↓↓↓
			/*
			 减库存
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if(updateCount <= 0) {
				//没有更新记录，秒杀结束
				throw new SeckillCloseException("seckill is closed.");
			}else {
				//记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount <= 0) {
					throw new RepeatKillException("seckill repeated.");
				}else {
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
				}
			}
			*/
			//简单优化 (降低了网络延迟和GC影响的时间)
			//记录购买行为
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if(insertCount <= 0) {
				//重复购买
				throw new RepeatKillException("seckill repeated.");
			}else {
				//减库存，热点商品竞争
				int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
				if(updateCount <= 0) {
					//没有更新记录，秒杀结束，rollback
					throw new SeckillCloseException("seckill is closed.");
				}else {
					//秒杀成功，commit
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
				}
			}
		}catch(SeckillCloseException e1) {
			throw e1;
		}catch(RepeatKillException e2) {
			throw e2;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			//所有编译器异常转化为运行期异常
			throw new SeckillException("seckill inner error: "+e.getMessage());
		}
	}

}
