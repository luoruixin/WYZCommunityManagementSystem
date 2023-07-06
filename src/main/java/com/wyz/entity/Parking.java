package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("parking")
public class Parking implements Serializable {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "user_id",updateStrategy = FieldStrategy.IGNORED)
    private Long userId;
    @TableField(value = "parking_num")
    private String parkingNum;
    @TableField(value = "area")
    private String area;
}
