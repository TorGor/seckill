package com.holy.nacosconsumerone.service;


import com.holy.nacosconsumerone.entity.Seckill;
import com.holy.nacosconsumerone.pojo.SeckillResult;
import com.holy.nacosconsumerone.pojo.UserInfo;

/**
 *
 */
public interface SecKillService {

	SeckillResult getAllSecKill() throws Exception;

	SeckillResult getSecKillDetail(String secKillId);

	SeckillResult getExposer(Long secKillId);

	SeckillResult executeSeckill(Long seckillId ,String md5, Long phone);



}