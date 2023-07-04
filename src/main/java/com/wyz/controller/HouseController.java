package com.wyz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.dto.HouseJsonByLevel;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.BindHouseFormDTO;
import com.wyz.dto.UserDTO;
import com.wyz.entity.House;
import com.wyz.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public R<List<HouseJsonByLevel>> selectByLevel(){

        return houseService.selectByLevel();

    }

    //删除绑定的房屋
    @DeleteMapping("/delete")
    public R<String> deleteHouse(@RequestParam String houseId){
        try{
            return houseService.deleteHouse(houseId);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("房屋删除失败，请重试");
        }
    }

    //分页查询
    @GetMapping("/page")
    public R<Page> pageR(int page, int pageSize){

        return houseService.pageR(page,pageSize);

    }


}
