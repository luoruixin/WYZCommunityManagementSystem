package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.HouseJson;
import com.wyz.common.R;
import com.wyz.dto.BindHouseFormDTO;
import com.wyz.entity.House;
import com.wyz.mapper.HouseMapper;
import com.wyz.service.HouseService;
import com.wyz.utils.UserHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {
    @Override
    public R<String> bindHouse(BindHouseFormDTO bindHouseFormDTO) {
        if(StrUtil.isEmpty(bindHouseFormDTO.getArea())
            ||StrUtil.isEmpty(bindHouseFormDTO.getArea())
            ||StrUtil.isEmpty(bindHouseFormDTO.getCell())
            ||StrUtil.isEmpty(bindHouseFormDTO.getHouseCode())
            ||StrUtil.isEmpty(bindHouseFormDTO.getRelation())){
            return R.error("请将信息填充完整");
        }
        House house = query().eq("area", bindHouseFormDTO.getArea())
                .eq("apart", bindHouseFormDTO.getApart())
                .eq("cell", bindHouseFormDTO.getCell())
                .eq("house_code", bindHouseFormDTO.getHouseCode()).one();
        if(house==null){
            return R.error("没有该房屋");
        }
        house.setUserId(UserHolder.getUser().getId());
        house.setRelation(bindHouseFormDTO.getRelation());

        updateById(house);
        return R.success("房屋关系绑定成功");
    }

    @Override
    public R<HouseJson> selectByLevel() {
        List<House> houses = query().eq("user_id", null).list();

        return null;
    }
}
