package com.wyz.dto;

import lombok.Data;

@Data
public class UpdatePhoneFormDTO {
    private String oldPhone;
    private String code;
    private String newPhone;
}
