package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("problem")
public class Problem {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "content")
    private String content;
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    @TableField(value = "category")
    private String category;
    @TableField(value = "status")
    private Integer status;
    @TableField(value = "video")
    private String videoName;
    @TableField(value = "image")
    private String imageName;

    @TableField(value = "title")
    private String title;
}
