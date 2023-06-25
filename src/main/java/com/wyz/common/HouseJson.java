package com.wyz.common;

import lombok.Data;

import java.util.Map;

@Data
public class HouseJson {

    private Map<String, Area> data;

    @Data
    public static class Area {
        private Map<String, Apart> buildings;
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
