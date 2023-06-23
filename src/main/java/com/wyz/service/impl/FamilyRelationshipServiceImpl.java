package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.Car;
import com.wyz.entity.FamilyRelationship;
import com.wyz.mapper.CarMapper;
import com.wyz.mapper.FamilyRelationshipMapper;
import com.wyz.service.CarService;
import com.wyz.service.FamilyRelationshipService;
import org.springframework.stereotype.Service;

@Service
public class FamilyRelationshipServiceImpl extends ServiceImpl<FamilyRelationshipMapper, FamilyRelationship> implements FamilyRelationshipService {
}
