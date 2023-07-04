package com.wyz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.dto.HouseJsonByLevel;
import com.wyz.common.R;
import com.wyz.dto.BindHouseFormDTO;
import com.wyz.entity.House;

import java.util.List;

public interface HouseService extends IService<House> {
    R<String> bindHouse(BindHouseFormDTO house);

    R<List<HouseJsonByLevel>> selectByLevel();

    R<String> deleteHouse(String houseId);

    R<Page> pageR(int page, int pageSize);
}
