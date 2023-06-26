package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.ParkingJsonByLevel;
import com.wyz.entity.Parking;
import com.wyz.mapper.ParkingMapper;
import com.wyz.service.ParkingService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper, Parking> implements ParkingService {
    @Override
    public R<ParkingJsonByLevel> selectUnused() {
        List<Parking> parkings = query().isNull("user_id").orderByAsc("parking_num").list();
        ParkingJsonByLevel parkingJsonByLevel=new ParkingJsonByLevel();
        Map<String, ParkingJsonByLevel.Area> areaMap = new HashMap<>();

        for (Parking parking : parkings) {
            String area = parking.getArea();
            String parkingNum = parking.getParkingNum();

            ParkingJsonByLevel.Area areaObj = areaMap.get(area);
            if (areaObj == null) {
                areaObj = new ParkingJsonByLevel.Area();
                areaObj.setCodes(new String[]{});
                areaMap.put(area, areaObj);
            }

            String[] codes = areaObj.getCodes();
            String[] newCodes = Arrays.copyOf(codes, codes.length + 1);
            newCodes[codes.length] = parkingNum;
            areaObj.setCodes(newCodes);
        }

        parkingJsonByLevel.setAreas(areaMap);
        return R.success(parkingJsonByLevel);
    }

    @Override
    public R<String> add(Parking parking) {
        if(StrUtil.isEmpty(parking.getParkingNum())||StrUtil.isEmpty(parking.getArea())){
            return R.error("请将信息填充完整");
        }
        Parking parking1 = query().eq("parking_num", parking.getParkingNum()).eq("area",parking.getArea()).one();
        if(parking1==null){
            return R.error("未查询到该车位的信息");
        }
        parking1.setUserId(UserHolder.getUser().getId());
        updateById(parking1);
        return R.success("添加成功");
    }

    @Override
    public R<String> delete(String id) {
        if(StrUtil.isEmpty(id)){
            return R.error("删除无效车位");
        }
        Parking parking = query().eq("id", id).one();
        if(parking==null|| !Objects.equals(parking.getUserId(), UserHolder.getUser().getId())){
            return R.error("删除无效车位");
        }
        parking.setUserId(null);
        updateById(parking);
        return R.success("解绑成功");
    }
}
