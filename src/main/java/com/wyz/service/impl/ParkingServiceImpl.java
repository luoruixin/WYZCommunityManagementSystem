package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.CustomException;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.HouseJsonByLevel;
import com.wyz.dto.ParkingJsonByLevel;
import com.wyz.entity.Parking;
import com.wyz.mapper.ParkingMapper;
import com.wyz.service.ParkingService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper, Parking> implements ParkingService {
    @Override
    public R<List<ParkingJsonByLevel>> selectUnused() {
        List<Parking> parkings = query().isNull("user_id").orderByAsc("parking_num").list();
        List<ParkingJsonByLevel> parkingJsonByLevelList=new ArrayList<>();

        for (Parking parking : parkings) {
            String parkingNum = parking.getParkingNum();
            String areaName = parking.getArea();

            ParkingJsonByLevel parkingJsonByLevel=null;
            for (ParkingJsonByLevel jsonByLevel : parkingJsonByLevelList) {
                if (jsonByLevel.getAreaName().equals(areaName)) {
                    parkingJsonByLevel = jsonByLevel;
                    break;
                }
            }
            if(parkingJsonByLevel==null){
                parkingJsonByLevel=new ParkingJsonByLevel();
                parkingJsonByLevel.setAreaName(areaName);
                parkingJsonByLevel.setCodes(new ArrayList<>());
                parkingJsonByLevel.getCodes().add(parkingNum);
                parkingJsonByLevelList.add(parkingJsonByLevel);
            }else {
                parkingJsonByLevel.getCodes().add(parkingNum);
            }

        }

        return R.success(parkingJsonByLevelList);
    }

    @Override
    public R<String> bindParking(Parking parking) {
        if(StrUtil.isEmpty(parking.getParkingNum())||StrUtil.isEmpty(parking.getArea())){
            throw new CustomException("请将信息填充完整");
        }
        Parking parking1 = query().eq("parking_num", parking.getParkingNum()).eq("area",parking.getArea()).one();
        if(parking1==null){
            throw new CustomException("未查询到该车位的信息");
        }
        parking1.setUserId(UserHolder.getUser().getId());
        updateById(parking1);
        return R.success("添加成功");
    }

    @Override
    public R<String> delete(String id) {
        if(StrUtil.isEmpty(id)){
            throw new CustomException("删除无效车位");
        }
        Parking parking = query().eq("id", id).one();
        if(parking==null|| !Objects.equals(parking.getUserId(), UserHolder.getUser().getId())){
            throw new CustomException("删除无效车位");
        }
        parking.setUserId(null);
        updateById(parking);
        return R.success("解绑成功");
    }
}
