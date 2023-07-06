package com.wyz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.CustomException;
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
        String area = bindHouseFormDTO.getArea();
        if(StrUtil.isEmpty(bindHouseFormDTO.getArea())
            ||StrUtil.isEmpty(bindHouseFormDTO.getArea())
            ||StrUtil.isEmpty(bindHouseFormDTO.getCell())
            ||StrUtil.isEmpty(bindHouseFormDTO.getHouseCode())
            ||StrUtil.isEmpty(bindHouseFormDTO.getRelation())){
            throw new CustomException("请将信息填充完整");
        }
        House house = query().eq("area", bindHouseFormDTO.getArea())
                .eq("apart", bindHouseFormDTO.getApart())
                .eq("cell", bindHouseFormDTO.getCell())
                .eq("house_code", bindHouseFormDTO.getHouseCode()).one();
        if(house==null){
            throw new CustomException("没有该房屋");
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

    //TODO:这里必须存在redis中
    @Override
    public R<List<HouseJsonByLevel>> selectByLevel() {
        List<House> houses = query().isNull("user_id").orderByAsc("num").list();
        List<HouseJsonByLevel> houseJsonByLevelList = new ArrayList<>();
        for (House house : houses) {
            String areaName = house.getArea();
            String apartName = house.getApart();
            String cellName = house.getCell();
            String houseCode = house.getHouseCode();

            HouseJsonByLevel houseJsonByLevel = null;
            for (HouseJsonByLevel jsonByLevel : houseJsonByLevelList) {
                if (jsonByLevel.getAreaName().equals(areaName)) {
                    houseJsonByLevel = jsonByLevel;
                    break;
                }
            }

            if(houseJsonByLevel==null){
                houseJsonByLevel=new HouseJsonByLevel();
                houseJsonByLevel.setAreaName(areaName);
                houseJsonByLevel.setApartList(new ArrayList<>());
                houseJsonByLevelList.add(houseJsonByLevel);
            }

            // Find or create the corresponding Apart object
            HouseJsonByLevel.Apart apart = null;
            for (HouseJsonByLevel.Apart existingApart : houseJsonByLevel.getApartList()) {
                if (existingApart.getApartName().equals(apartName)) {
                    apart = existingApart;
                    break;
                }
            }

            if (apart == null) {
                apart = new HouseJsonByLevel.Apart();
                apart.setApartName(apartName);
                apart.setCellList(new ArrayList<>());
                houseJsonByLevel.getApartList().add(apart);
            }

            // Find or create the corresponding Cell object
            HouseJsonByLevel.Cell cell = null;
            for (HouseJsonByLevel.Cell existingCell : apart.getCellList()) {
                if (existingCell.getCellName().equals(cellName)) {
                    cell = existingCell;
                    break;
                }
            }
            if (cell == null) {
                cell = new HouseJsonByLevel.Cell();
                cell.setCellName(cellName);
                cell.setHouseCodeList(new ArrayList<>());
                apart.getCellList().add(cell);
            }

            // Add the houseCode to the houseList
            cell.getHouseCodeList().add(houseCode);
        }
        return R.success(houseJsonByLevelList);
    }

    @Override
    @Transactional
    public R<String> deleteHouse(String houseId) {
        if(StrUtil.isEmpty(houseId)){
            throw new CustomException("id为空");
        }
        House house = query().eq("id", houseId).one();
        if(house.getUserId()==null){
            throw new CustomException("当前房屋还未进行绑定");
        }
        if(!Objects.equals(house.getUserId(), UserHolder.getUser().getId())){
            throw new CustomException("该房屋并不属于您");
        }
        house.setUserId(null);
        house.setRelation(null);
        updateById(house);

        House house1 = query().eq("user_id", UserHolder.getUser().getId()).one();
        if(house1==null){
            //说明刚刚删除的是最后一栋房屋
            User user = userService.getById(UserHolder.getUser().getId());
            user.setExamine(0);
            user.setType(1);
            userService.updateById(user);
        }

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
                throw new CustomException(null);
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
