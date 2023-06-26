package com.wyz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.HouseJson;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.BindHouseFormDTO;
import com.wyz.entity.House;
import com.wyz.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
            return R.error("房屋查询失败，请重试");
        }
    }

    //删除绑定的房屋
    @DeleteMapping("/delete")
    public R<String> deleteHouse(@RequestParam String houseId){
        try{
            return houseService.deleteHouse(houseId);
        }catch (Exception e){
            return R.error("房屋查询失败，请重试");
        }
    }

    //分页查询
    @GetMapping("/page")
    public R<Page> pageR(int page, int pageSize){
        //构造分页构造器对象
        Page<House> pageInfo=new Page<>(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<House> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(House::getUserId,UserHolder.getUser().getId());

        houseService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }
}
