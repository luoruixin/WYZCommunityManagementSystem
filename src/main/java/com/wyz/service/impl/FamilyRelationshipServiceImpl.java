package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.dto.AddFamilyFormDTO;
import com.wyz.entity.FamilyRelationship;
import com.wyz.mapper.FamilyRelationshipMapper;
import com.wyz.service.FamilyRelationshipService;
import com.wyz.common.UserHolder;
import org.springframework.stereotype.Service;

@Service
public class FamilyRelationshipServiceImpl extends ServiceImpl<FamilyRelationshipMapper, FamilyRelationship> implements FamilyRelationshipService {
    @Override
    public R<String> addFamily(AddFamilyFormDTO addFamilyFormDTO) {
        if(StrUtil.isEmpty(addFamilyFormDTO.getIdCard())
            ||StrUtil.isEmpty(addFamilyFormDTO.getName())
            ||StrUtil.isEmpty(addFamilyFormDTO.getRelation())){
            return R.error("请将信息补充完整");
        }
        FamilyRelationship familyRelationship=new FamilyRelationship();
        if(UserHolder.getUser().getExamine()==0)
        {
            return R.error("您还未进行房屋绑定");
        }
        familyRelationship.setUserId(UserHolder.getUser().getId());
        familyRelationship.setName(addFamilyFormDTO.getName());
        familyRelationship.setIdCard(addFamilyFormDTO.getIdCard());
        familyRelationship.setRelation(addFamilyFormDTO.getRelation());
        save(familyRelationship);
        return R.success("添加成功");
    }
}
