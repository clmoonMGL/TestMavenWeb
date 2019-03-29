package com.newsdoo.seckill.entity;

import java.util.Date;

public class SuccessKilled {

	private Long seckillId;
	
	private Long usePhone;
	
	private short state;
	
	private Date createTime;
	
	private Seckill seckill;

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public Long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

	public Long getUsePhone() {
		return usePhone;
	}

	public void setUsePhone(Long usePhone) {
		this.usePhone = usePhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SuccessKilled [seckillId=" + seckillId + 
				", usePhone=" + usePhone + 
				", state=" + state + 
				", createTime="
				+ createTime + "]";
	}
	
	
	
}
