package com.wyz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.UserDTO;
import com.wyz.dto.VoteInfoDTO;
import com.wyz.entity.House;
import com.wyz.entity.User;
import com.wyz.entity.VoteInfo;
import com.wyz.entity.VoteRecord;
import com.wyz.mapper.VoteInfoMapper;
import com.wyz.service.HouseService;
import com.wyz.service.UserService;
import com.wyz.service.VoteInfoService;
import com.wyz.service.VoteRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class VoteInfoServiceImpl extends ServiceImpl<VoteInfoMapper, VoteInfo> implements VoteInfoService {
    @Autowired
    private HouseService houseService;
    @Autowired
    private VoteRecordService voteRecordService;
    @Autowired
    private UserService userService;
    @Override
    @Transactional
    public R<String> publish(VoteInfoDTO voteInfoDto) {
        if(StrUtil.isEmpty(voteInfoDto.getContent())
            ||voteInfoDto.getEndTime()==null
            ||StrUtil.isEmpty(voteInfoDto.getTitle())
            ||StrUtil.isEmpty(voteInfoDto.getApart())
            ||StrUtil.isEmpty(voteInfoDto.getCategory())){
            return R.error("请将信息填充完整");
        }
        voteInfoDto.setJoinCode(null);
        String apart = voteInfoDto.getApart();

        String apartNum=new String();
        if(!apart.contains("全部")){
            House house = houseService.query().eq("apart", apart).last("limit 1").one();
            if(house==null){
                return R.error("您选择的楼栋无效");
            }
            String num = house.getNum();
            if(StrUtil.isEmpty(num)){
                return R.error("发布无效");
            }
            apartNum = num.substring(2, 4);
        }else {
            apartNum="00";
        }

        String areaNum = houseService.query().eq("user_id", UserHolder.getUser().getId()).one().getNum().substring(0, 2);
        voteInfoDto.setJoinCode(areaNum+apartNum);
        voteInfoDto.setCreateUserId(UserHolder.getUser().getId());
        voteInfoDto.setStartTime(LocalDate.now());

        VoteInfo voteInfo=new VoteInfo();
        BeanUtil.copyProperties(voteInfoDto,voteInfo);
        save(voteInfo);
        return R.success("发布成功");
    }

    @Override
    public R<Page> pageR(int page, int pageSize, String condition) {
        log.info("page={},pageSize={},name={}",page,pageSize,condition);

        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<VoteInfo> queryWrapper=new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper = queryWrapper.like(condition!=null,VoteInfo::getCategory, condition).or().like(condition!=null,VoteInfo::getTitle, condition);
        //添加排序条件
        queryWrapper.orderByDesc(VoteInfo::getStartTime);
        page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @Override
    public R<Page> pageMe(int page, int pageSize, String condition) {
        UserDTO user = UserHolder.getUser();
        log.info("page={},pageSize={},name={}",page,pageSize,condition);

        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<VoteInfo> queryWrapper=new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper = queryWrapper.eq(VoteInfo::getCreateUserId,user.getId()).and(wapper->wapper.like(condition!=null,VoteInfo::getCategory, condition).or().like(condition!=null,VoteInfo::getTitle, condition));
        //添加排序条件
        queryWrapper.orderByDesc(VoteInfo::getStartTime);
        page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @Override
    public R<String> deleteVote(Long id) {
        if(id==null){
            return R.error("删除无效");
        }
        removeById(id);
        LambdaQueryWrapper<VoteRecord> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper=queryWrapper.eq(VoteRecord::getVoteInfoId, id);
        voteRecordService.remove(queryWrapper);
        return R.success("删除成功");
    }

    @Override
    @Transactional
    public R<VoteInfoDTO> getDetails(Long id) {
        if(id==null){
            return R.error("无效");
        }
        VoteInfo voteInfo = query().eq("id", id).one();
        VoteInfoDTO voteInfoDTO=new VoteInfoDTO();
        BeanUtil.copyProperties(voteInfo,voteInfoDTO);
        Long createUserId = voteInfo.getCreateUserId();
        User user = userService.getById(createUserId);
        voteInfoDTO.setName(user.getName());
        return R.success(voteInfoDTO);
    }
}
