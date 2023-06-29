package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("vote_record")
public class VoteRecord {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "v_info_id")
    private Long voteInfoId;
    @TableField(value = "vote_time")
    private LocalDateTime voteTime;
    @TableField(value = "vote_type")
    private Integer voteType;
}
