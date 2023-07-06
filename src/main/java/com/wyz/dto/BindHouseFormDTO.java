package com.wyz.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BindHouseFormDTO implements Serializable {
    private String area;
    private String apart;
    private String cell;
    private String houseCode;
    private String relation;
}
