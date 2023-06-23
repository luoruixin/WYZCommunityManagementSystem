package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("car")
public class Car {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "num")
    private String num;
    @TableField(value = "brand")
    private String brand;
    @TableField(value = "vin")
    private String vin;
    @TableField(value = "create_time")
    private LocalDateTime createTime;
}
