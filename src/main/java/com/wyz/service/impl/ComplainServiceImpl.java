package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.CustomException;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.ComplainDTO;
import com.wyz.dto.ProblemDTO;
import com.wyz.entity.Car;
import com.wyz.entity.Complain;
import com.wyz.entity.FamilyRelationship;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.ComplainMapper;
import com.wyz.service.CarService;
import com.wyz.service.ComplainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ComplainServiceImpl extends ServiceImpl<ComplainMapper, Complain> implements ComplainService {

    @Autowired
    private ComplainMapper complainMapper;
    @Override
    public R<String> addComplain(Complain complain) {
        String content = complain.getContent();
        String title = complain.getTitle();
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        List<Complain> complainList = query().eq("user_id", UserHolder.getUser().getId()).like("create_time", localDate).list();
        if (complainList.size()>2){
            //防止一天内投诉的问题过多
            return R.error("您已经达到今天的上限");
        }
        if(StrUtil.isEmpty(content)||StrUtil.isEmpty(title)){
            return R.error("请将信息填充完整");
        }
        complain.setUserId(UserHolder.getUser().getId());
        complain.setCreateTime(LocalDateTime.now());
        save(complain);
        return R.success("添加成功");
    }

    @Override
    public R<Page> pageMe(int page, int pageSize) {
        log.info("page={},pageSize={}",page,pageSize);
        //构造分页构造器对象
        Page<Complain> pageInfo=new Page<>(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Complain> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.eq(Complain::getUserId,UserHolder.getUser().getId()).orderByDesc(Complain::getCreateTime);

        page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @Override
    @Transactional
    public R<Page> pageAll(int page, int pageSize, String condition) {
        log.info("page={},pageSize={}",page,pageSize);
        //构造分页构造器对象
        Page<ComplainDTO> pageInfo=new Page<>(page,pageSize);

        List<ComplainDTO> complainDTOList = complainMapper.pageAll(page, pageSize, condition);
        pageInfo.setRecords(complainDTOList);

        Integer total=complainMapper.getPageAllTotal();
        pageInfo.setTotal(total);
        return R.success(pageInfo);
    }

    @Override
    public R<ComplainDTO> getDetail(Long id) {
        if(id==null){
            throw new CustomException("id为空");
        }
        ComplainDTO complainDTO=complainMapper.getDetail(id);
        return R.success(complainDTO);
    }
}
