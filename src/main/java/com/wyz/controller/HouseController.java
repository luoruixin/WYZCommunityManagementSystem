package com.wyz.controller;

import com.wyz.common.HouseJson;
import com.wyz.common.R;
import com.wyz.dto.BindHouseFormDTO;
import com.wyz.entity.House;
import com.wyz.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    //绑定房屋
    @PostMapping("/bindHouse")
    public R<String> bindHouse(@RequestBody BindHouseFormDTO bindHouseFormDTO){
        try{
            return houseService.bindHouse(bindHouseFormDTO);
        }catch (Exception e){
            return R.error("房屋绑定失败，请重试");
        }
    }

    //房屋的分级查询
    @GetMapping("/selectByLevel")
    public R<HouseJson> selectByLevel(){
        try{
            return houseService.selectByLevel();
        }catch (Exception e){
            return R.error("房屋绑定失败，请重试");
        }
    }
}
