package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.ProblemDTO;
import com.wyz.dto.UserDTO;
import com.wyz.dto.VoteRecordDTO;
import com.wyz.entity.Problem;
import com.wyz.mapper.ProblemMapper;
import com.wyz.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Override
    public R<String> report(Problem problem) {
        if(StrUtil.isEmpty(problem.getContent())
            ||StrUtil.isEmpty(problem.getCategory())
            ||StrUtil.isEmpty(problem.getTitle())){
            return R.success("请将信息填充完整");
        }
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        List<Problem> problemList = query().eq("user_id", UserHolder.getUser().getId()).like("create_time", localDate).list();
        if(problemList.size()>2){
            //防止一天内上报问题的次数过多
            return R.error("您已经达到今天的上限");
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

    @Override
    @Transactional
    public R<Page> pageAll(int page, int pageSize, String condition) {
        log.info("page={},pageSize={}",page,pageSize);
        //构造分页构造器
        Page pageInfo=new Page();

        List<ProblemDTO> problemDTOList = problemMapper.pageAllProblem(page-1,pageSize,condition);
        pageInfo.setRecords(problemDTOList);

        Integer total=problemMapper.getPageAllProblemTotal();
        pageInfo.setTotal(total);
        return R.success(pageInfo);
    }

    @Override
    public R<ProblemDTO> getDetail(Long id) {
        if(id==null){
            return R.error("查询失败");
        }
        return R.success(problemMapper.getDetail(id));
    }
}
