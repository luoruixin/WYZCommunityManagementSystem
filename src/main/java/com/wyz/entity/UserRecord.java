package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user_record")
public class UserRecord implements Serializable {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "username")
    private String username;
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "name")
    private String name;
    @TableField(value = "time")
    private LocalDateTime time;
    @TableField(value = "out_time")
    private LocalDateTime outTime;
    @TableField(value = "id_card" )
    private String idCard;
}
