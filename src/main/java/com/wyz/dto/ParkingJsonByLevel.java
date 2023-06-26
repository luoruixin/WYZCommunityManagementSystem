package com.wyz.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ParkingJsonByLevel {
    private Map<String, Area> areas;

    @Data
    public static class Area {
        private String[] codes;
    }

}
