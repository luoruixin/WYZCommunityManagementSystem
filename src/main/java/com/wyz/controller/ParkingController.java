package com.wyz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.ParkingJsonByLevel;
import com.wyz.entity.House;
import com.wyz.entity.Parking;
import com.wyz.service.ParkingService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/parking")
public class ParkingController {
    @Autowired
    private ParkingService parkingService;

    //绑定车位
    @PostMapping("/bindParking")
    @CacheEvict(value = "parking:pageR",allEntries = true)
    public R<String> bindParking(@RequestBody Parking parking){

        return parkingService.bindParking(parking);

    }

    //查询还未被绑定的车位
    @GetMapping("/selectUnused")
    public R<List<ParkingJsonByLevel>> selectUnused(){

        return parkingService.selectUnused();

    }

    //删除车位
    @DeleteMapping("/delete")
    @CacheEvict(value = "parking:pageR",allEntries = true)
    public R<String> delete(@RequestParam String id){

        return parkingService.delete(id);

    }

    //分页查询
    @GetMapping("/page")
    @Cacheable(value = "parking:pageR",key = "#page+'-'+#pageSize+'-'+T(java.lang.String).valueOf(T(com.wyz.common.UserHolder).getUser().getId())")
    public R<Page> pageR(int page,int pageSize){
        //构造分页构造器对象
        Page<Parking> pageInfo=new Page<>(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Parking> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHolder.getUser().getId()!=null,Parking::getUserId, UserHolder.getUser().getId())
                .isNotNull(Parking::getUserId);

        parkingService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }
}
