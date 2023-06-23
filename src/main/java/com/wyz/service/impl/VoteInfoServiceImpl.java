package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.Car;
import com.wyz.entity.VoteInfo;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.VoteInfoMapper;
import com.wyz.service.CarService;
import com.wyz.service.VoteInfoService;
import org.springframework.stereotype.Service;

@Service
public class VoteInfoServiceImpl extends ServiceImpl<VoteInfoMapper, VoteInfo> implements VoteInfoService {
}
