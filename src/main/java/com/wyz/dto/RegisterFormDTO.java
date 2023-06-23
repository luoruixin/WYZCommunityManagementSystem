package com.wyz.dto;

import lombok.Data;

@Data
public class RegisterFormDTO {
    private String phone;
    private String code;
    private String nickname;
    private String password;
}
