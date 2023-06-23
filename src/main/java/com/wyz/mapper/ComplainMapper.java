package com.wyz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyz.entity.Car;
import com.wyz.entity.Complain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComplainMapper extends BaseMapper<Complain> {
}
