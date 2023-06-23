package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.Car;
import com.wyz.entity.Problem;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.ProblemMapper;
import com.wyz.service.CarService;
import com.wyz.service.ProblemService;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
}
