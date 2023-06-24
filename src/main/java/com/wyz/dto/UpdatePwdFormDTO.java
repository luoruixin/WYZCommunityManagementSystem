package com.wyz.dto;

import lombok.Data;

@Data
public class UpdatePwdFormDTO {
    private String oldPassword;
    private String newPassword;
}
