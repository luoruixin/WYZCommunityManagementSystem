package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("family_relationship")
public class FamilyRelationship implements Serializable {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "family_id")
    private Long familyId;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "relation")
    private String relation;
    @TableField(value = "name")
    private String name;
    @TableField(value = "id_card")
    private String idCard;
    @TableField(value = "sex")
    private Integer sex;
    @TableField(value = "age")
    private Integer age;
}
