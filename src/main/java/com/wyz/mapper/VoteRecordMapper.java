package com.wyz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyz.dto.VoteRecordDTO;
import com.wyz.entity.Car;
import com.wyz.entity.VoteRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VoteRecordMapper extends BaseMapper<VoteRecord> {

    @Results(id = "VoteRecordDTOResultMap", value = {
            @Result(property = "voteInfoId", column = "v_info_id")
    })
    @Select("select * from vote_record inner join vote_info on vote_record.v_info_id=vote_info.id where vote_record.user_id=#{userId} limit #{page},#{pageSize};")
    public List<VoteRecordDTO> selectMe(@Param("page") int page, @Param("pageSize") int pageSize, @Param("userId") Long userId);
}
