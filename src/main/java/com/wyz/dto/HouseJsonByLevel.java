package com.wyz.dto;

import lombok.Data;

import java.util.Map;

@Data
public class HouseJsonByLevel {

    private Map<String, Area> areas;

    @Data
    public static class Area {
        private Map<String, Apart> aparts;
    }

    @Data
    public static class Apart {
        private Map<String, Cell> cells;
    }

    @Data
    public static class Cell {
        private String[] codes;
    }
}
