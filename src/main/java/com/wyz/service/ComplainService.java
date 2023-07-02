package com.wyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.entity.Car;
import com.wyz.entity.Complain;

public interface ComplainService extends IService<Complain> {
    R<String> addComplain(Complain complain);
}
