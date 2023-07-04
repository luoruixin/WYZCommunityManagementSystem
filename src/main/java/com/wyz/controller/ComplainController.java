package com.wyz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.entity.Complain;
import com.wyz.service.ComplainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/complain")
public class ComplainController {
    @Autowired
    private ComplainService complainService;
    @PostMapping("/add")
    public R<String> addComplain(@RequestBody Complain complain){
        return complainService.addComplain(complain);
    }

    @GetMapping("/pageMe")
    public R<Page> pageMe(int page,int pageSize){
        return complainService.pageMe(page,pageSize);
    }
}
