package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.Car;
import com.wyz.entity.Problem;
import com.wyz.entity.ProblemFeedback;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.ProblemFeedbackMapper;
import com.wyz.service.CarService;
import com.wyz.service.ProblemFeedbackService;
import org.springframework.stereotype.Service;

@Service
public class ProblemFeedbackServiceImpl extends ServiceImpl<ProblemFeedbackMapper, ProblemFeedback> implements ProblemFeedbackService {
}
