package com.wyz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.dto.FamilyFormDTO;
import com.wyz.entity.FamilyRelationship;
import com.wyz.service.FamilyRelationshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/familyRelationship")
public class FamilyRelationshipController {

    @Autowired
    private FamilyRelationshipService familyRelationshipService;

    //添加家庭成员
    @PostMapping("/addFamily")
    public R<String> addFamily(@RequestBody FamilyFormDTO familyFormDTO){
        try {
            return familyRelationshipService.addFamily(familyFormDTO);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("添加家庭成员失败");
        }
    }

    //删除家庭成员
    @DeleteMapping("/delete")
    public R<String> deleteFamily(@RequestParam Long id){
        try {
            return familyRelationshipService.deleteFamily(id);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("删除家庭成员失败");
        }
    }

    //分页查询
    @GetMapping("/page")
    public R<Page> pageR(int page,int pageSize){
        try {
            return familyRelationshipService.pageR(page,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("网络状况不佳，请稍后");
        }
    }

    //修改家人信息
    @PutMapping("/update")
    public R<String> update(@RequestBody FamilyRelationship familyRelationship){
        try {
            return familyRelationshipService.updateFamily(familyRelationship);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("修改失败");
        }
    }
}
