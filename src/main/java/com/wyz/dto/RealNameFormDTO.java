package com.wyz.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RealNameFormDTO implements Serializable {
    private String name;
    private String idCard;
}
