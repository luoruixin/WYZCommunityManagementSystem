package com.wyz.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginFormDTO implements Serializable {
    private String phone;
    private String code;
    private String password;
    private String nickname;
}
