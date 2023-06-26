package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.dto.AddFamilyFormDTO;
import com.wyz.entity.FamilyRelationship;
import com.wyz.entity.User;
import com.wyz.mapper.FamilyRelationshipMapper;
import com.wyz.service.FamilyRelationshipService;
import com.wyz.common.UserHolder;
import com.wyz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyRelationshipServiceImpl extends ServiceImpl<FamilyRelationshipMapper, FamilyRelationship> implements FamilyRelationshipService {
    @Autowired
    private UserService userService;
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

    @Override
    public R<String> deleteFamily(Long id) {
        if(id==null){
            return R.error("删除无效");
        }

        FamilyRelationship familyRelationship = query().eq("family_id", id).one();
        removeById(familyRelationship.getId());
        User user = userService.getById(id);
        if(user!=null){
            user.setType(1);
            user.setExamine(0);
            userService.updateById(user);
        }

        return R.success("删除成功");
    }
}
