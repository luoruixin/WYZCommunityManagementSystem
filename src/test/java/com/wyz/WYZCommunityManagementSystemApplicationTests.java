package com.wyz;

import com.wyz.common.HouseJson;
import com.wyz.common.R;
import com.wyz.entity.House;
import com.wyz.service.HouseService;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

@SpringBootTest
class WYZCommunityManagementSystemApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HouseService houseService;

    @Test
    void contextLoads() {
        List<House> houses = houseService.query().isNull("user_id").list();
        Collections.sort(houses);
        HouseJson houseJson = new HouseJson();
        Map<String, HouseJson.Area> areaMap = new HashMap<>();

        for (House house : houses) {
            String area = house.getArea();
            String apart = house.getApart();
            String cell = house.getCell();
            String houseCode = house.getHouseCode();

            HouseJson.Area areaObj = areaMap.get(area);
            if (areaObj == null) {
                areaObj = new HouseJson.Area();
                areaMap.put(area, areaObj);
            }

            Map<String, HouseJson.Apart> apartMap = areaObj.getBuildings();
            if (apartMap == null) {
                apartMap = new HashMap<>();
                areaObj.setBuildings(apartMap);
            }

            HouseJson.Apart apartObj = apartMap.get(apart);
            if (apartObj == null) {
                apartObj = new HouseJson.Apart();
                apartMap.put(apart, apartObj);
            }

            Map<String, HouseJson.Cell> cellMap = apartObj.getCells();
            if (cellMap == null) {
                cellMap = new HashMap<>();
                apartObj.setCells(cellMap);
            }

            HouseJson.Cell cellObj = cellMap.get(cell);
            if (cellObj == null) {
                cellObj = new HouseJson.Cell();
                cellObj.setCodes(new String[]{});
                cellMap.put(cell, cellObj);
            }

            String[] codes = cellObj.getCodes();
            String[] newCodes = Arrays.copyOf(codes, codes.length + 1);
            newCodes[codes.length] = houseCode;
            cellObj.setCodes(newCodes);
        }

        houseJson.setData(areaMap);
        System.out.println(houseJson);
    }

}
