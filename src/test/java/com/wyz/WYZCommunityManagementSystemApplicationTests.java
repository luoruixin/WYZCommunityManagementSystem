package com.wyz;

import cn.hutool.core.util.StrUtil;
import com.wyz.service.HouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.File;
import java.util.UUID;

@SpringBootTest
class WYZCommunityManagementSystemApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HouseService houseService;

    @Test
    void contextLoads() {
        createNewFileName("12345.jpg");
    }

    private void createNewFileName(String originalFilename) {
        // 获取后缀
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        // 生成目录
        String name = UUID.randomUUID().toString();
        int hash = name.hashCode();
        int d1 = hash & 0xF;
        int d2 = (hash >> 4) & 0xF;
        // 判断目录是否存在
        File dir = new File("d:/", StrUtil.format("/{}/{}", d1, d2));
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
        // 生成文件名
        System.out.println(StrUtil.format("/{}/{}/{}.{}", d1, d2, name, suffix));
    }
}
