package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.Car;
import com.wyz.entity.Complain;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.ComplainMapper;
import com.wyz.service.CarService;
import com.wyz.service.ComplainService;
import org.springframework.stereotype.Service;

@Service
public class ComplainServiceImpl extends ServiceImpl<ComplainMapper, Complain> implements ComplainService {
}
