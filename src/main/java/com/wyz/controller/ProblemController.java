package com.wyz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.entity.Problem;
import com.wyz.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    //问题上报
    @PostMapping("/report")
    @Caching(evict = {
            @CacheEvict(value = "problemCache:pageMe", allEntries = true),
//            @CacheEvict(value = "committeeProblemCache:pageR", key = "'pageR'")
    })
    public R<String> report(@RequestBody Problem problem){

        return problemService.report(problem);

    }

    //问题分页查询
    @GetMapping("/pageMe")
    @Cacheable(value = "problemCache:pageMe",key = "#page+'-'+#pageSize+'-'+T(java.lang.String).valueOf(T(com.wyz.common.UserHolder).getUser().getId())")
    public R<Page> pageMe(int page,int pageSize){

        return problemService.pageMe(page,pageSize);

    }

    //修改问题
    @PutMapping("/update")
    @CacheEvict(value = "problemCache:pageMe",allEntries = true)
    public R<String> update(@RequestBody Problem problem){

        return problemService.updateProblem(problem);

    }

    //删除问题
    @DeleteMapping("/delete")
    @Caching(evict = {
            @CacheEvict(value = "problemCache:pageMe",allEntries = true),
            @CacheEvict(value = "committeeProblemCache:pageR", allEntries = true)
    })
    public R<String> delete(@RequestParam("id") Long id){

        return problemService.deleteProblem(id);

    }

}
