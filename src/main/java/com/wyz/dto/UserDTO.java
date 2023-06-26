package com.wyz.dto;

import lombok.Data;

//和user类相比没有password和idcard
@Data
public class UserDTO {
    private Long id;
    private String nickname;
    private String name;
    private Integer type;
    private Integer examine;
    private Integer age;
    private Integer sex;
    private String phone;
    private String idCard;
}
