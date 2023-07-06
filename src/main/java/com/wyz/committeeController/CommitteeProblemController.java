package com.wyz.committeeController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.dto.ProblemDTO;
import com.wyz.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/committeeProblem")
public class CommitteeProblemController {

    @Autowired
    private ProblemService problemService;
    @GetMapping("/page")
    @Cacheable(value = "committeeProblemCache:pageR",key = "#page+#pageSize",condition = "#condition==null")
    public R<Page> pageR(int page,int pageSize,String condition){
        return problemService.pageAll(page,pageSize,condition);
    }

    @GetMapping("/detail")
    @Cacheable(value = "committeeProblem:getDetail",key = "#id")
    public R<ProblemDTO> getDetail(@RequestParam("id")Long id){
        return problemService.getDetail(id);
    }
}
