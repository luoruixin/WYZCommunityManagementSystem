package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.Car;
import com.wyz.entity.House;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.HouseMapper;
import com.wyz.service.CarService;
import com.wyz.service.HouseService;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {
}
