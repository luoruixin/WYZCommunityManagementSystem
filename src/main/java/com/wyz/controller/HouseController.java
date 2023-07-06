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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = "house:pageR",allEntries = true)
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
    @CacheEvict(value = "house:pageR",allEntries = true)
    public R<String> deleteHouse(@RequestParam("id") String houseId){
        return houseService.deleteHouse(houseId);
    }

    //分页查询
    @GetMapping("/page")
    @Cacheable(value = "house:pageR",key = "#page+#pageSize")
    public R<Page> pageR(int page, int pageSize){

        return houseService.pageR(page,pageSize);

    }
}
