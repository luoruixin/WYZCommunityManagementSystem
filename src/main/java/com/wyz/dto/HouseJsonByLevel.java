package com.wyz.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HouseJsonByLevel {

    private String areaName;
    private List<Apart> apartList;


    @Data
    public static class Apart {
        private String apartName;
        private List<Cell> cellList;
    }

    @Data
    public static class Cell {
        private String cellName;
        private List<String> houseCodeList;
    }
}
