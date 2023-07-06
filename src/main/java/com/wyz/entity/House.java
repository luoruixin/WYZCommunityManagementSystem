package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Comparator;

@Data
@TableName("house")
public class House implements Serializable {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "user_id",updateStrategy = FieldStrategy.IGNORED)   //,updateStrategy = FieldStrategy.IGNORED表示可以将其设置为null
    private Long userId;
    @TableField(value = "area")
    private String area;
    @TableField(value = "apart")
    private String apart;
    @TableField(value = "cell")
    private String cell;
    @TableField(value = "house_code")
    private String houseCode;
    @TableField(value = "relation",updateStrategy = FieldStrategy.IGNORED)
    private String relation;
    @TableField(value = "num")
    private String num;
}
