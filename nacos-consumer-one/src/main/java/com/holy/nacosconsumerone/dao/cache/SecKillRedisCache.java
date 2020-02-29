package com.holy.nacosconsumerone.dao.cache;

import com.holy.nacosconsumerone.pojo.SeckillResult;

import java.util.List;

public interface SecKillRedisCache {

    SeckillResult getAllSecKill();

    SeckillResult getSecKillDetail(String secKillId);

    SeckillResult getExposer(Long secKillId);

}
