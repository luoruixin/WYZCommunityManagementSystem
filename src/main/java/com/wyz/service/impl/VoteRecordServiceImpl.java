package com.wyz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.UserDTO;
import com.wyz.dto.VoteRecordDTO;
import com.wyz.entity.House;
import com.wyz.entity.VoteInfo;
import com.wyz.entity.VoteRecord;
import com.wyz.mapper.VoteInfoMapper;
import com.wyz.mapper.VoteRecordMapper;
import com.wyz.service.HouseService;
import com.wyz.service.VoteInfoService;
import com.wyz.service.VoteRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VoteRecordServiceImpl extends ServiceImpl<VoteRecordMapper, VoteRecord> implements VoteRecordService {
    @Autowired
    private HouseService houseService;
    @Autowired
    private VoteInfoService voteInfoService;
    @Autowired
    private VoteInfoMapper voteInfoMapper;
    @Autowired
    private VoteRecordService voteRecordService;
    @Autowired
    private VoteRecordMapper voteRecordMapper;

    @Override
    @Transactional
    public R<String> joinVote(Long id, Integer type) {
        VoteInfo voteInfo = voteInfoService.query().eq("id", id).one();
        if(id==null||type==null||voteInfo==null){
            return R.error("参与无效");
        }
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        VoteRecord record = query().eq("user_id", userId).eq("v_info_id", id).one();
        if(record!=null){
            return R.error("您已参加过一次了");
        }
        List<House> houseList = houseService.query().eq("user_id", userId).list();
        if(houseList==null){
            return R.error("您无权参加");
        }

        //一个人可能买了多套房屋
        List<String> houseNumList=new ArrayList<>();
        for (House house : houseList) {
            String num = house.getNum();
            houseNumList.add(num);
        }

        if(voteInfo.getEndTime().isBefore(LocalDate.now())){
            return R.error("投票已结束");
        }

        if(voteInfo.getJoinCode().substring(2,4).equals("00")){
            for (String houseNum : houseNumList) {
                if (houseNum.startsWith(voteInfo.getJoinCode().substring(0,2))) {
                    VoteRecord voteRecord=new VoteRecord();
                    voteRecord.setVoteInfoId(id);
                    voteRecord.setVoteTime(LocalDateTime.now());
                    voteRecord.setUserId(userId);
                    voteRecord.setVoteType(type);
                    save(voteRecord);
                    return R.success("投票成功");
                }
            }
            return R.error("您无权参加");
        }
        for (String houseNum : houseNumList) {
            //如果没有一个houseNum对应于当前的joincode
            if(!houseNum.startsWith(voteInfo.getJoinCode())){
                return R.error("您无权参加");
            }
        }

        VoteRecord voteRecord=new VoteRecord();
        voteRecord.setVoteInfoId(id);
        voteRecord.setVoteTime(LocalDateTime.now());
        voteRecord.setUserId(userId);
        voteRecord.setVoteType(type);
        save(voteRecord);

        return R.success("投票成功");
    }

    @Override
    @Transactional
    public R<Page> pageMe(int page, int pageSize) {
        UserDTO user = UserHolder.getUser();
        log.info("page={},pageSize={}",page,pageSize);
        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);

        List<VoteRecordDTO> voteRecordDTOList = voteRecordMapper.selectMe((page-1)*pageSize, pageSize, user.getId());
        pageInfo.setRecords(voteRecordDTOList);

        pageInfo.setTotal(voteRecordMapper.getSelectMeTotal(user.getId()));
        return R.success(pageInfo);
    }

    //TODO:添加total
    @Override
    @Transactional
    public R<Page> pageCan(int page, int pageSize) {
        log.info("page={},pageSize={}",page,pageSize);
        UserDTO user = UserHolder.getUser();
        LocalDate localDate=LocalDate.now();
        List<String> houseNumList = houseService.query().eq("user_id", user.getId()).list().stream().map(s -> s.getNum()).collect(Collectors.toList());

        //voteInfoList1是所有满足条件的集合
        List<VoteInfo> voteInfoList1=new ArrayList<>();
        //先分页查询
        for (String houseNum : houseNumList) {
            List<VoteInfo> voteInfoList = voteInfoMapper.selectPageCan((page-1)*pageSize, pageSize, localDate, houseNum);
            voteInfoList1.addAll(voteInfoList);
        }
        //再去除已经投票了的
        ListIterator<VoteInfo> iterator=voteInfoList1.listIterator();
        while (iterator.hasNext()){
            VoteInfo voteInfo = iterator.next();
            LambdaQueryWrapper<VoteRecord> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper = queryWrapper.eq(VoteRecord::getVoteInfoId, voteInfo.getId()).eq(VoteRecord::getUserId, user.getId());
            VoteRecord voteRecord = voteRecordService.getOne(queryWrapper);
            if(voteRecord!=null){
                iterator.remove();
                //将指针前置
//                iterator.previous();
            }
        }

        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);
        pageInfo.setRecords(voteInfoList1);

        return R.success(pageInfo);
    }
}
