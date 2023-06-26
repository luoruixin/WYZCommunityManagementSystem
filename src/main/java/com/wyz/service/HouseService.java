package com.wyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.dto.HouseJsonByLevel;
import com.wyz.common.R;
import com.wyz.dto.BindHouseFormDTO;
import com.wyz.entity.House;

public interface HouseService extends IService<House> {
    R<String> bindHouse(BindHouseFormDTO house);

    R<HouseJsonByLevel> selectByLevel();

    R<String> deleteHouse(String houseId);
}
