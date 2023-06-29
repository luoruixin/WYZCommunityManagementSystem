package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("vote_info")
public class VoteInfo {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "create_user_id")
    private Long createUserId;
    @TableField(value = "start_time")
    private LocalDate startTime;
    @TableField(value = "end_time")
    private LocalDate endTime;
    @TableField(value = "category")
    private String category;
    @TableField(value = "content")
    private String content;
    @TableField(value = "join_code")
    private String joinCode;
    @TableField(value = "title")
    private String title;
}
