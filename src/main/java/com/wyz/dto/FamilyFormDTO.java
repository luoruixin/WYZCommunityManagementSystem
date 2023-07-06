package com.wyz.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FamilyFormDTO implements Serializable {
    private String name;
    private String idCard;
    private String relation;
    private Integer sex;
    private Integer age;
}
