package com.wyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.entity.Car;

public interface CarService extends IService<Car> {
    R<String> add(Car car);

    R<String> delete(Long id);
}
