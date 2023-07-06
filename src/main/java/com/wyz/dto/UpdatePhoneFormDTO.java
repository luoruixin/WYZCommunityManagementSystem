package com.wyz.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePhoneFormDTO implements Serializable {
    private String oldPhone;
    private String code;
    private String newPhone;
}
