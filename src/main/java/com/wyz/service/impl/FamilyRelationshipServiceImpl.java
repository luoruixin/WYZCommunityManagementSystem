package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.CustomException;
import com.wyz.common.R;
import com.wyz.dto.FamilyFormDTO;
import com.wyz.entity.FamilyRelationship;
import com.wyz.entity.User;
import com.wyz.mapper.FamilyRelationshipMapper;
import com.wyz.service.FamilyRelationshipService;
import com.wyz.common.UserHolder;
import com.wyz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FamilyRelationshipServiceImpl extends ServiceImpl<FamilyRelationshipMapper, FamilyRelationship> implements FamilyRelationshipService {
    @Autowired
    private UserService userService;
    @Override
    @Transactional
    public R<String> addFamily(FamilyFormDTO familyFormDTO) {
        if(StrUtil.isEmpty(familyFormDTO.getIdCard())
            ||StrUtil.isEmpty(familyFormDTO.getName())
            ||StrUtil.isEmpty(familyFormDTO.getRelation())
            || familyFormDTO.getAge()==null|| familyFormDTO.getSex()==null){
            throw new CustomException("请将信息补充完整");
        }
        String name = familyFormDTO.getName();
        String idCard = familyFormDTO.getIdCard();
        String relation = familyFormDTO.getRelation();

        FamilyRelationship familyRelationship=new FamilyRelationship();
        if(UserHolder.getUser().getExamine()==0)
        {
            throw new CustomException("您还未进行房屋绑定");
        }
        familyRelationship.setUserId(UserHolder.getUser().getId());
        familyRelationship.setName(name);
        familyRelationship.setIdCard(idCard);
        familyRelationship.setRelation(relation);
        familyRelationship.setAge(familyFormDTO.getAge());
        familyRelationship.setSex(familyFormDTO.getSex());

        //在user表中查询是否原来就存在该用户的账号
        User user = userService.query().eq("id_card", idCard).eq("name", name).one();
        if(user!=null){
            //表示user表中原来就存在该用户的账号
            user.setType(0);
            user.setExamine(1);
            userService.updateById(user);
            familyRelationship.setFamilyId(user.getId());
        }
        save(familyRelationship);
        return R.success("添加成功");
    }

    @Override
    public R<String> deleteFamily(Long id) {
        if(id==null){
            throw new CustomException("删除无效");
        }

        FamilyRelationship familyRelationship = query().eq("id", id).one();
        removeById(familyRelationship.getId());
        User user = userService.getById(id);
        if(user!=null){
            user.setType(1);
            user.setExamine(0);
            userService.updateById(user);
        }

        return R.success("删除成功");
    }

    @Override
    public R<Page> pageR(int page, int pageSize) {
        //构造分页构造器对象
        Page<FamilyRelationship> pageInfo=new Page<>(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<FamilyRelationship> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.eq(FamilyRelationship::getUserId,UserHolder.getUser().getId())
                .isNotNull(FamilyRelationship::getUserId);

        page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @Override
    public R<String> updateFamily(FamilyRelationship familyRelationship) {
        if(StrUtil.isEmpty(familyRelationship.getIdCard())
                ||StrUtil.isEmpty(familyRelationship.getName())
                ||StrUtil.isEmpty(familyRelationship.getRelation())
                || familyRelationship.getAge()==null|| familyRelationship.getSex()==null){
            throw new CustomException("请将信息补充完整");
        }

        if(UserHolder.getUser().getExamine()==0)
        {
            throw new CustomException("您还未进行房屋绑定");
        }
        updateById(familyRelationship);
        return R.success("修改成功");
    }
}
