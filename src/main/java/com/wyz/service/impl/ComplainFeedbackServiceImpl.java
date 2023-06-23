package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.Car;
import com.wyz.entity.ComplainFeedback;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.ComplainFeedbackMapper;
import com.wyz.service.CarService;
import com.wyz.service.ComplainFeedbackService;
import org.springframework.stereotype.Service;

@Service
public class ComplainFeedbackServiceImpl extends ServiceImpl<ComplainFeedbackMapper, ComplainFeedback> implements ComplainFeedbackService {
}
