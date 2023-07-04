package com.wyz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyz.dto.ComplainDTO;
import com.wyz.dto.ProblemDTO;
import com.wyz.entity.Car;
import com.wyz.entity.Complain;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComplainMapper extends BaseMapper<Complain> {
    public List<ComplainDTO> pageAll(int page, int pageSize, String condition);

    Integer getPageAllTotal();

    ComplainDTO getDetail(Long id);
}
