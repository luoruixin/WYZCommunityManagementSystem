package com.wyz.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePwdFormDTO implements Serializable {
    private String oldPassword;
    private String newPassword;
}
