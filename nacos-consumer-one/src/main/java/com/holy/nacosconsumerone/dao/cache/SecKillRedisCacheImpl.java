package com.holy.nacosconsumerone.dao.cache;

import com.holy.nacosconsumerone.pojo.SeckillResult;
import com.holy.nacosconsumerone.utils.RedisUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class SecKillRedisCacheImpl implements SecKillRedisCache{

    @Resource
    private RedisUtils redisUtil;

    @Override
    public SeckillResult getAllSecKill() {

        return null;
    }

    @Override
    public SeckillResult getSecKillDetail(String secKillId) {
        return null;
    }

    @Override
    public SeckillResult getExposer(Long secKillId) {
        return null;
    }
}
