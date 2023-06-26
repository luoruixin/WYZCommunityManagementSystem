package com.wyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.dto.AddFamilyFormDTO;
import com.wyz.entity.Car;
import com.wyz.entity.FamilyRelationship;

public interface FamilyRelationshipService extends IService<FamilyRelationship> {
    R<String> addFamily(AddFamilyFormDTO addFamilyFormDTO);

    R<String> deleteFamily(Long id);
}
