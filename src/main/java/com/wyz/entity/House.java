package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Comparator;

@Data
@TableName("house")
public class House{
    @TableId(value = "id")
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "area")
    private String area;
    @TableField(value = "apart")
    private String apart;
    @TableField(value = "cell")
    private String cell;
    @TableField(value = "house_code")
    private String houseCode;
    @TableField(value = "relation")
    private String relation;
    @TableField(value = "num")
    private String num;
}
