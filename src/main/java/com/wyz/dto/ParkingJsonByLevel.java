package com.wyz.dto;

import cn.hutool.core.lang.copier.SrcToDestCopier;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ParkingJsonByLevel {
    private String areaName;
    private List<String> codes;


}
