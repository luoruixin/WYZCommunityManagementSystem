package com.wyz.controller;

import com.wyz.common.R;
import com.wyz.dto.AddFamilyFormDTO;
import com.wyz.service.FamilyRelationshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/familyRelationship")
public class FamilyRelationshipController {

    @Autowired
    private FamilyRelationshipService familyRelationshipService;

    //添加家庭成员
    @PostMapping("/addFamily")
    public R<String> addFamily(@RequestBody AddFamilyFormDTO addFamilyFormDTO){
        try {
            return familyRelationshipService.addFamily(addFamilyFormDTO);
        }catch (Exception e){
            return R.error("添加家庭成员成功");
        }
    }
}
