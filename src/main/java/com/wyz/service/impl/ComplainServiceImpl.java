package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.entity.Car;
import com.wyz.entity.Complain;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.ComplainMapper;
import com.wyz.service.CarService;
import com.wyz.service.ComplainService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ComplainServiceImpl extends ServiceImpl<ComplainMapper, Complain> implements ComplainService {
    @Override
    public R<String> addComplain(Complain complain) {
        String content = complain.getContent();
        String title = complain.getTitle();
        if(StrUtil.isEmpty(content)||StrUtil.isEmpty(title)){
            return R.error("请将信息填充完整");
        }
        complain.setUserId(UserHolder.getUser().getId());
        complain.setCreateTime(LocalDateTime.now());
        save(complain);
        return R.success("添加成功");
    }
}
