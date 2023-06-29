package com.wyz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyz.entity.Car;
import com.wyz.entity.VoteInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface VoteInfoMapper extends BaseMapper<VoteInfo> {
    @Select("select * from vote_info where end_time>#{localDate} and #{houseNum} like concat(join_code,'%') limit #{page},#{pageSize};")
    public List<VoteInfo> selectPageCan(@Param("page") int page,@Param("pageSize") int pageSize,@Param("localDate") LocalDate localDate,@Param("houseNum") String houseNum);
}
