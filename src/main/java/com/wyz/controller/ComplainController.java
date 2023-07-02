package com.wyz.controller;

import com.wyz.common.R;
import com.wyz.entity.Complain;
import com.wyz.service.ComplainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
