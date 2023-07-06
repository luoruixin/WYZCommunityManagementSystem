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
            @CacheEvict(value = "problemCache", key = "'pageMe'"),
//            @CacheEvict(value = "committeeProblemCache", key = "'pageR'")
    })
    public R<String> report(@RequestBody Problem problem){
        try {
            return problemService.report(problem);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("上报失败");
        }
    }

    //问题分页查询
    @GetMapping("/pageMe")
    @Cacheable(value = "problemCache",key = "'pageMe'")
    public R<Page> pageMe(int page,int pageSize){

        return problemService.pageMe(page,pageSize);

    }

    //修改问题
    @PutMapping("/update")
    @CacheEvict(value = "problemCache",key = "'pageMe'")
    public R<String> update(@RequestBody Problem problem){
        try {
            return problemService.updateProblem(problem);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("修改失败");
        }
    }

    //删除问题
    @DeleteMapping("/delete")
    @Caching(evict = {
            @CacheEvict(value = "problemCache", key = "'pageMe'"),
            @CacheEvict(value = "committeeProblemCache", key = "'pageR'")
    })
    public R<String> delete(@RequestParam("id") Long id){
        try {
            return problemService.deleteProblem(id);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("更新失败");
        }
    }

}
