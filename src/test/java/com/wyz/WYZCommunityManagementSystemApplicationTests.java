package com.wyz;

import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class WYZCommunityManagementSystemApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        String phone="13275697035";
        String cacheCode=stringRedisTemplate.opsForValue().get("code:"+phone);
        System.out.println(cacheCode);
    }

}
