package com.holy.nacosconsumerone.configuration;

import com.holy.nacosconsumerone.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Configuration
public class RedisConfigurations {

    private static final Logger lg = LoggerFactory.getLogger(RedisConfigurations.class);

    //lettuce客户端连接工厂
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 设置RedisTemplate的序列化方式
     */
    public void setSerializer(RedisTemplate<String, Object> template) {

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        JdkSerializationRedisSerializer jdkRedisSeriz = new JdkSerializationRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);

        // value序列化方式采用jackson
//        template.setValueSerializer(stringRedisSerializer);

        // value 序列化采用 jdk 序列化
        template.setValueSerializer(jdkRedisSeriz);

        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);

        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(stringRedisSerializer);

        template.afterPropertiesSet();
    }


    /**
     * 配置redisTemplate 注入方式使用@Resource(name="") 方式注入
     */
    @Bean
    public RedisUtils defaultRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        setSerializer(template);
        template.afterPropertiesSet();
        return new RedisUtils(template);
    }

}