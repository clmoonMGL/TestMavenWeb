package com.newsdoo.seckill.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newsdoo.seckill.entity.Seckill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	
	private final Logger logger= LoggerFactory.getLogger(this.getClass());
	
	private final JedisPool jedisPool;
	
	public RedisDao(String ip,int port) {
		jedisPool = new JedisPool(ip, port);
	}
	
	public Seckill getSeckill(long seckillId) {
		//redis操作逻辑(不应该放在service层，毕竟是数据访问层的逻辑)
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckillId;
				//redis和jedis并没有实现序列化操作
				//get --> byte[] --> 反序列化 --> Object(Seckill)
				//采用自定义序列化
				
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public String putSeckill(Seckill seckill) {
		return null;
	}
	
}
