package com.wyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.HouseJson;
import com.wyz.common.R;
import com.wyz.dto.BindHouseFormDTO;
import com.wyz.entity.House;

public interface HouseService extends IService<House> {
    R<String> bindHouse(BindHouseFormDTO house);

    R<HouseJson> selectByLevel();
}
