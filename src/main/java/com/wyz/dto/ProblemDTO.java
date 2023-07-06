package com.wyz.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ProblemDTO implements Serializable {
    private Long id;
    private Long userId;
    private String content;
    private LocalDateTime createTime;
    private String category;
    private Integer status;
    private String videoName;
    private String imageName;
    private String title;
    private String name;
    private String idCard;
    private String phone;
}
