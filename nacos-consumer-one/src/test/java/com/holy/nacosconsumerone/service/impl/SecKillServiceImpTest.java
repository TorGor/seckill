package com.holy.nacosconsumerone.service.impl;

import com.holy.nacosconsumerone.utils.Constants;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.util.Random;

import static org.junit.Assert.*;

public class SecKillServiceImpTest {

    @Test
    public void getExposer() {
        String base = 1000 + "/" + Constants.SECKILL_MD5_SALT;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        System.out.println(md5);
    }

    @Test
    public void getRandom(){
        Random rm = new Random();

        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, 11);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        System.out.println(fixLenthString);
    }
}