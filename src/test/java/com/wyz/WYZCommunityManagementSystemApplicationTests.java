package com.wyz;

import com.wyz.service.HouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class WYZCommunityManagementSystemApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HouseService houseService;

    @Test
    void contextLoads() {
        boolean b = houseService.removeById(6);
        System.out.println(b);
    }

}
