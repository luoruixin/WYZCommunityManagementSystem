package com.wyz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.entity.Complain;
import com.wyz.service.ComplainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/complain")
public class ComplainController {
    @Autowired
    private ComplainService complainService;
    @PostMapping("/add")
    @Caching(evict = {
            @CacheEvict(value = "complain:pageMe",allEntries = true),
//            @CacheEvict(value = "CommitteeComplain:pageAll", allEntries = true)
    })
    public R<String> addComplain(@RequestBody Complain complain){
        return complainService.addComplain(complain);
    }

    @GetMapping("/pageMe")
    @Cacheable(value = "complain:pageMe",key = "#page+#pageSize")
    public R<Page> pageMe(int page,int pageSize){
        return complainService.pageMe(page,pageSize);
    }
}
