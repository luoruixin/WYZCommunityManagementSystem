package com.wyz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.entity.Car;
import com.wyz.entity.Parking;
import com.wyz.service.CarService;
import com.wyz.service.ParkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    //添加车位
    @PostMapping("/add")
    public R<String> add(@RequestBody Car car){
        try {
            return carService.add(car);
        }catch (Exception e){
            return R.error("网络繁忙，请重试");
        }
    }

    //删除车辆
    @DeleteMapping("/delete")
    public R<String> delete(@RequestParam Long id){
        try {
            return carService.delete(id);
        }catch (Exception e){
            return R.error("网络繁忙，请重试");
        }
    }

    //分页查询
    @GetMapping("/page")
    public R<Page> pageR(int page,int pageSize){
        //构造分页构造器对象
        Page<Car> pageInfo=new Page<>(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Car> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHolder.getUser().getId()!=null,Car::getUserId, UserHolder.getUser().getId())
                .isNotNull(Car::getUserId);

        carService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    //
}
