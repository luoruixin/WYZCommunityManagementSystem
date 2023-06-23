package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.Car;
import com.wyz.entity.HouseRecord;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.HouseRecordMapper;
import com.wyz.service.CarService;
import com.wyz.service.HouseRecordService;
import org.springframework.stereotype.Service;

@Service
public class HouseRecordServiceImpl extends ServiceImpl<HouseRecordMapper, HouseRecord> implements HouseRecordService {
}
