package com.wyz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyz.dto.ProblemDTO;
import com.wyz.entity.Car;
import com.wyz.entity.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {
//    @Select("select * from problem inner join user on problem.user_id = user.id where category like '%' || #{condition} || '%' OR title LIKE '%' || #{condition} || '%' limit #{page},#{pageSize};")
    public List<ProblemDTO> pageAllProblem(@Param("page") int page,@Param("pageSize") int pageSize,@Param("condition") String condition);
}
