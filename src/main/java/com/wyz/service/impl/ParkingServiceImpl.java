package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.Car;
import com.wyz.entity.Parking;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.ParkingMapper;
import com.wyz.service.CarService;
import com.wyz.service.ParkingService;
import org.springframework.stereotype.Service;

@Service
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper, Parking> implements ParkingService {
}
