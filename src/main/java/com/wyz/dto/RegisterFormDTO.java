package com.wyz.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterFormDTO implements Serializable {
    private String phone;
    private String code;
    private String nickname;
    private String password;
}
