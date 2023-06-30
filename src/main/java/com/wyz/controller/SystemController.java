package com.wyz.controller;

import com.wyz.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/dictionary")
    public R<Map<Object,Object>> getDictionary(){
        Map<Object, Object> dictionaryMap = stringRedisTemplate.opsForHash().entries("dictionary");
        return R.success(dictionaryMap);
    }
}
