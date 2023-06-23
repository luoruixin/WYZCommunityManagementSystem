package com.wyz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyz.entity.Car;
import com.wyz.entity.Problem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {
}
