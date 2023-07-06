package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("problem_feedback")
public class ProblemFeedback implements Serializable {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "problem_id")
    private Long problemId;
    @TableField(value = "content")
    private String content;
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    @TableField(value = "title")
    private String title;
}
