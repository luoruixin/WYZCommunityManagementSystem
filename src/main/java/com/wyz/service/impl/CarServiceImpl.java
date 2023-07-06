package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.CustomException;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.entity.Car;
import com.wyz.mapper.CarMapper;
import com.wyz.service.CarService;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {
    @Override
    public R<String> add(Car car) {
        if(StrUtil.isEmpty(car.getNum())
            ||StrUtil.isEmpty(car.getBrand())
            ||StrUtil.isEmpty(car.getVin())){
            throw new CustomException("请将信息填充完整");
        }
        car.setUserId(UserHolder.getUser().getId());
        car.setCreateTime(LocalDateTime.now());
        save(car);
        return R.success("添加成功");
    }

    @Override
    public R<String> delete(Long id) {
        if(id==null){
            throw new CustomException("请重新选择车辆");
        }
        removeById(id);
        return R.success("删除成功");
    }

    @Override
    public R<String> updateCar(Car car) {
        if(StrUtil.isEmpty(car.getNum())
                ||StrUtil.isEmpty(car.getBrand())
                ||StrUtil.isEmpty(car.getVin())
                ||car.getId()==null){
            throw new CustomException("请将信息填充完整");
        }
        updateById(car);
        return R.success("修改成功");
    }
}
