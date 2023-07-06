package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("complain")
public class Complain implements Serializable {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "content")
    private String content;
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "title")
    private String title;
}
