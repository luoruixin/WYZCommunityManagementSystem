package com.wyz.committeeController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/committeeProblem")
public class CommitteeProblemController {

    @Autowired
    private ProblemService problemService;
    @GetMapping("/page")
    public R<Page> pageR(int page,int pageSize,String condition){
        return problemService.pageAll(page,pageSize,condition);
    }
}
