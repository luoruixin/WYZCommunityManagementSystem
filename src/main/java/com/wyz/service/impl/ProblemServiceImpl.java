package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.entity.Problem;
import com.wyz.mapper.ProblemMapper;
import com.wyz.service.ProblemService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
    @Override
    public R<String> report(Problem problem) {
        if(StrUtil.isEmpty(problem.getContent())
            ||StrUtil.isEmpty(problem.getCategory())
            ||StrUtil.isEmpty(problem.getTitle())){
            return R.success("请将信息填充完整");
        }
        problem.setUserId(UserHolder.getUser().getId());
        problem.setStatus(0);
        problem.setCreateTime(LocalDateTime.now());
        save(problem);
        return R.success("上报成功");
    }

    @Override
    public R<Page> pageMe(int page, int pageSize) {
        //构造分页构造器对象
        Page<Problem> pageInfo=new Page<>(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Problem> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHolder.getUser().getId()!=null,Problem::getUserId, UserHolder.getUser().getId())
                .isNotNull(Problem::getUserId);

        page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @Override
    public R<String> updateProblem(Problem problem) {
        if(StrUtil.isEmpty(problem.getContent())
                ||StrUtil.isEmpty(problem.getCategory())
                ||StrUtil.isEmpty(problem.getTitle())){
            return R.success("请将信息填充完整");
        }
        updateById(problem);
        return R.success("更新成功");
    }

    @Override
    public R<String> deleteProblem(Long id) {
        if(id==null){
            return R.error("删除无效");
        }
        removeById(id);
        return R.success("删除成功");
    }
}
