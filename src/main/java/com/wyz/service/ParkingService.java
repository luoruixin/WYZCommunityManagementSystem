package com.wyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.dto.ParkingJsonByLevel;
import com.wyz.entity.Parking;

public interface ParkingService extends IService<Parking> {
    R<ParkingJsonByLevel> selectUnused();

    R<String> bindParking(Parking parking);

    R<String> delete(String id);
}
