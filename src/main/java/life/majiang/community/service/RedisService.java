package life.majiang.community.service;

import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

public class RedisService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

}

