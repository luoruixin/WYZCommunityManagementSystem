package com.wyz.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user")
public class User implements Serializable {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "username")
    private String nickname;  //昵称
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "password" )
    private String password;
    @TableField(value = "id_card" )
    private String idCard;
    @TableField(value = "name")
    private String name;
    @TableField(value = "type")
    private Integer type;
    @TableField(value = "examine")
    private Integer examine;
    @TableField(value = "age")
    private Integer age;
    @TableField(value = "sex")
    private Integer sex;

}
