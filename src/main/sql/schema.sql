CREATE DATABASE seckill;

use seckill;

-- 秒杀仓库库存表
CREATE TABLE seckill(
    `seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
    `name` varchar(120) NOT NULL COMMENT '商品名称',
    `number` int NOT NULL COMMENT '库存数量',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `start_time` timestamp NOT NULL COMMENT '秒杀开启时间',
    `end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
    PRIMARY KEY (seckill_id),
    KEY idx_start_time(start_time),
    KEY idx_end_time(end_time),
    KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT = 1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
insert into 
	seckill(name,number,start_time,end_time)
values
	('1000元秒杀iPhone6',100,'2019-03-25 00:00:00','2019-03-26 00:00:00'),
	('500元秒杀ipad2',200,'2019-03-25 00:00:00','2019-03-26 00:00:00'),
	('300元秒杀ipad2',300,'2019-03-25 00:00:00','2019-03-26 00:00:00'),
	('200元秒杀红米note',400,'2019-03-25 00:00:00','2019-03-26 00:00:00');
	
-- 秒杀成功明细表
CREATE TABLE success_killed(
	`seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
	`user_phone` BIGINT NOT NULL COMMENT '用户手机号',
	`state` TINYINT NOT NULL DEFAULT -1 COMMENT '标识状态 -1 无效 0成功 1已付款 2 已发货 ',
	`create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
	PRIMARY KEY (seckill_id,user_phone),/*联合主键*/
	KEY idx_create_time(create_time)
)ENGINE =InnoDB DEFAULT CHARSET =utf8 COMMENT ='秒杀成功明细';

-- 数据库控制台
mysql -uroot -p
