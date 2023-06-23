package com.wyz.dto;

import lombok.Data;

@Data
public class FoundPasswordFormDTO {
    private String phone;
    private String code;
    private String newPassword;
}
