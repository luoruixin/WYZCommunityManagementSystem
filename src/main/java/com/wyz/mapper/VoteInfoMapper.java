package com.wyz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyz.entity.Car;
import com.wyz.entity.VoteInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VoteInfoMapper extends BaseMapper<VoteInfo> {
}
