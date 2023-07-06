package com.wyz.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FoundPasswordFormDTO implements Serializable {
    private String phone;
    private String code;
    private String newPassword;
}
