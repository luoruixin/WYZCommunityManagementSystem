package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.Car;
import com.wyz.entity.VoteRecord;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.VoteRecordMapper;
import com.wyz.service.CarService;
import com.wyz.service.VoteRecordService;
import org.springframework.stereotype.Service;

@Service
public class VoteRecordServiceImpl extends ServiceImpl<VoteRecordMapper, VoteRecord> implements VoteRecordService {
}
