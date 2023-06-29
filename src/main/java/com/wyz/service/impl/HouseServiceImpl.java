package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.dto.HouseJsonByLevel;
import com.wyz.common.R;
import com.wyz.dto.BindHouseFormDTO;
import com.wyz.dto.UserDTO;
import com.wyz.entity.House;
import com.wyz.entity.HouseRecord;
import com.wyz.entity.User;
import com.wyz.mapper.HouseMapper;
import com.wyz.service.FamilyRelationshipService;
import com.wyz.service.HouseRecordService;
import com.wyz.service.HouseService;
import com.wyz.service.UserService;
import com.wyz.common.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    @Autowired
    private HouseRecordService houseRecordService;
    @Autowired
    private UserService userService;
    @Autowired
    private FamilyRelationshipService familyRelationshipService;
    @Override
    @Transactional  //这里要操纵house和houseRecord两张表
    public R<String> bindHouse(BindHouseFormDTO bindHouseFormDTO) {
        if(StrUtil.isEmpty(bindHouseFormDTO.getArea())
            ||StrUtil.isEmpty(bindHouseFormDTO.getArea())
            ||StrUtil.isEmpty(bindHouseFormDTO.getCell())
            ||StrUtil.isEmpty(bindHouseFormDTO.getHouseCode())
            ||StrUtil.isEmpty(bindHouseFormDTO.getRelation())){
            return R.error("请将信息填充完整");
        }
        House house = query().eq("area", bindHouseFormDTO.getArea())
                .eq("apart", bindHouseFormDTO.getApart())
                .eq("cell", bindHouseFormDTO.getCell())
                .eq("house_code", bindHouseFormDTO.getHouseCode()).one();
        if(house==null){
            return R.error("没有该房屋");
        }
        house.setUserId(UserHolder.getUser().getId());
        house.setRelation(bindHouseFormDTO.getRelation());
        updateById(house);

        //将房屋信息添加到house_record表中
        HouseRecord houseRecord=new HouseRecord();
        houseRecord.setHouseId(house.getId());
        houseRecord.setStartTime(LocalDateTime.now());
        //暂时来说，居住的人是当前户主
        houseRecord.setIdCard(UserHolder.getUser().getIdCard());
        houseRecord.setName(UserHolder.getUser().getName());
        houseRecordService.save(houseRecord);
        return R.success("房屋关系绑定成功");
    }

    @Override
    public R<HouseJsonByLevel> selectByLevel() {
        List<House> houses = query().isNull("user_id").orderByAsc("num").list();
        HouseJsonByLevel houseJsonByLevel = new HouseJsonByLevel();
        Map<String, HouseJsonByLevel.Area> areaMap = new HashMap<>();

        for (House house : houses) {
            String area = house.getArea();
            String apart = house.getApart();
            String cell = house.getCell();
            String houseCode = house.getHouseCode();

            HouseJsonByLevel.Area areaObj = areaMap.get(area);
            if (areaObj == null) {
                areaObj = new HouseJsonByLevel.Area();
                areaMap.put(area, areaObj);
            }

            Map<String, HouseJsonByLevel.Apart> apartMap = areaObj.getAparts();
            if (apartMap == null) {
                apartMap = new HashMap<>();
                areaObj.setAparts(apartMap);
            }

            HouseJsonByLevel.Apart apartObj = apartMap.get(apart);
            if (apartObj == null) {
                apartObj = new HouseJsonByLevel.Apart();
                apartMap.put(apart, apartObj);
            }

            Map<String, HouseJsonByLevel.Cell> cellMap = apartObj.getCells();
            if (cellMap == null) {
                cellMap = new HashMap<>();
                apartObj.setCells(cellMap);
            }

            HouseJsonByLevel.Cell cellObj = cellMap.get(cell);
            if (cellObj == null) {
                cellObj = new HouseJsonByLevel.Cell();
                cellObj.setCodes(new String[]{});
                cellMap.put(cell, cellObj);
            }

            String[] codes = cellObj.getCodes();
            String[] newCodes = Arrays.copyOf(codes, codes.length + 1);
            newCodes[codes.length] = houseCode;
            cellObj.setCodes(newCodes);
        }

        houseJsonByLevel.setAreas(areaMap);
        return R.success(houseJsonByLevel);
    }

    @Override
    @Transactional
    public R<String> deleteHouse(String houseId) {
        if(StrUtil.isEmpty(houseId)){
            return R.error("id为空");
        }
        House house = query().eq("id", houseId).one();
        if(house.getUserId()==null){
            return R.error("当前房屋还未进行绑定");
        }
        if(!Objects.equals(house.getUserId(), UserHolder.getUser().getId())){
            return R.error("该房屋并不属于您");
        }
        house.setUserId(null);
        house.setRelation(null);
        updateById(house);

        //在house_record表中填写退房时间
        HouseRecord houseRecord=houseRecordService.query().eq("house_id",house.getId()).one();
        houseRecord.setEndTime(LocalDateTime.now());
        houseRecordService.updateById(houseRecord);

        return R.success("删除成功");
    }

    @Override
    public R<Page> pageR(int page, int pageSize) {
        //构造分页构造器对象
        Page<House> pageInfo=new Page<>(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<House> queryWrapper=new LambdaQueryWrapper<>();
        UserDTO currentUser = UserHolder.getUser();


        if(currentUser.getType()==0&&currentUser.getExamine()==1){
            //该情况出现在家人来查询的情况

            Long ownerId = familyRelationshipService.query().eq("family_id", currentUser.getId()).one().getUserId();
            if(ownerId==null){
                return R.error(null);
            }
            queryWrapper.eq(House::getUserId,ownerId)
                    .isNotNull(House::getUserId);

            page(pageInfo,queryWrapper);

            return R.success(pageInfo);
        }
        queryWrapper.eq(House::getUserId,currentUser.getId())
                .isNotNull(House::getUserId);

        page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

}
