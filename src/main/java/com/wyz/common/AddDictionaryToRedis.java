package com.wyz.common;

import com.wyz.entity.Dictionary;
import com.wyz.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//项目启动时就将数据字典表加入到redis中
@Component
public class AddDictionaryToRedis implements ApplicationRunner {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DictionaryService dictionaryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries("dictionary");
        if(map!=null&&map.size()!=0){
            return;
        }
        List<Dictionary> dictionaryList = dictionaryService.list();
        Map<String,String> dictionaryMap=new HashMap<>();
        for (Dictionary dictionary : dictionaryList) {
            String name = dictionary.getName();
            String itemValues = dictionary.getItemValues();
            dictionaryMap.put(name,itemValues);
        }
        stringRedisTemplate.opsForHash().putAll("dictionary",dictionaryMap);
    }
}
