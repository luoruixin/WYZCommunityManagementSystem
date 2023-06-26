package com.wyz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.dto.FamilyFormDTO;
import com.wyz.entity.FamilyRelationship;

public interface FamilyRelationshipService extends IService<FamilyRelationship> {
    R<String> addFamily(FamilyFormDTO familyFormDTO);

    R<String> deleteFamily(Long id);

    R<Page> pageR(int page, int pageSize);

    R<String> updateFamily(FamilyRelationship familyRelationship);
}
