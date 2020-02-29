package com.holy.nacosconsumerone.service.impl;

import com.holy.nacosconsumerone.dao.SeckillDao;
import com.holy.nacosconsumerone.dao.SuccessKilledDao;
import com.holy.nacosconsumerone.entity.Seckill;
import com.holy.nacosconsumerone.pojo.Exposer;
import com.holy.nacosconsumerone.pojo.SeckillResult;
import com.holy.nacosconsumerone.pojo.exception.RepeatKillException;
import com.holy.nacosconsumerone.pojo.exception.SeckillCloseException;
import com.holy.nacosconsumerone.pojo.exception.SeckillException;
import com.holy.nacosconsumerone.service.SecKillService;
import com.holy.nacosconsumerone.utils.Constants;
import com.holy.nacosconsumerone.utils.RedisUtils;
import com.holy.nacosconsumerone.utils.SerializeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class SecKillServiceImp implements SecKillService {

    @Resource
    private SeckillDao seckillDao;

    @Resource
    private SuccessKilledDao successKilledDao;

    @Resource
    private RedisUtils redisUtils;

    /**
     * @return
     */
    @Override
    public SeckillResult getAllSecKill() throws Exception {
        List<Seckill> listLimt = seckillDao.queryAll(0, 5);
        if (listLimt == null || listLimt.size() < 1)
            return new SeckillResult(false, listLimt);
        for (Seckill seckill : listLimt) {
            String redisId = getIdForRedis(seckill.getSeckillId());
            byte[] serialized = SerializeUtil.serialize(seckill);
            // 将数据放入缓存
            redisUtils.set(redisId, serialized, 1000);
            System.out.println(SerializeUtil.deserialize((byte[]) redisUtils.get(redisId)).toString());
        }
        return new SeckillResult(true, listLimt);
    }

    private String getIdForRedis(Long seckillId) {
        return "seckillId:" + seckillId;
    }

    @Override
    public SeckillResult getSecKillDetail(String secKillId) {
        return null;
    }

    /**
     * @param secKillId
     * @return
     */
    @Override
    public SeckillResult getExposer(Long secKillId) {
        SeckillResult result;
        if (secKillId == null) {
            return new SeckillResult(false, "没有对应商品");
        }
        Date nowTime = new Date();
        Seckill seckillDetail = seckillDao.queryById(secKillId);
        if (nowTime.getTime() < seckillDetail.getStartTime().getTime() || nowTime.getTime() > seckillDetail.getEndTime().getTime()) {
            return new SeckillResult(false, "不在开奖时间");
        }
        // md5 url
        String md5 = getMD5(secKillId);
        return new SeckillResult(true, new Exposer(true, md5, secKillId));
    }

    /**
     * @param seckillId
     * @param md5
     * @param phone
     * @return
     */
    @Override
    public SeckillResult executeSeckill(Long seckillId, String md5, Long phone) {
        if (StringUtils.isBlank(md5) || seckillId == null || !md5.equals(getMD5(seckillId)))
            return new SeckillResult(false, "商品数据不存在");

        Date nowTime = new Date();
        // 先插入秒杀成功信息，如果所有请求过来先减少库存，会造成重复秒杀，然后还要回滚数据库，考虑使用 redis 优化
        phone = Long.valueOf(getFixLenthString(10));
        int updateSuccess = successKilledDao.insertSuccessKilled(seckillId, phone);
        if (updateSuccess <= 0) {
            return new SeckillResult(false, "重复秒杀");
        } else {
            int reducecount = seckillDao.reduceNumber(seckillId, nowTime);
            if (reducecount <= 0) {
                return new SeckillResult(false,"秒杀结束");
            }
        }
        System.out.println("====== " + "秒杀成功");
        return new SeckillResult(true,"秒杀成功");
    }

    /**
     *
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId) {
        String base = seckillId + "/" + Constants.SECKILL_MD5_SALT;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 返回长度为【strLength】的随机数，在前面补0
     */
    private static String getFixLenthString(int strLength) {

        Random rm = new Random();

        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(2, strLength + 2);
    }


}
