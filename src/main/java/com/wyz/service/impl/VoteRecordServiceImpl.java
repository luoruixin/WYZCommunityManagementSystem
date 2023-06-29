package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.UserDTO;
import com.wyz.entity.House;
import com.wyz.entity.VoteInfo;
import com.wyz.entity.VoteRecord;
import com.wyz.mapper.VoteRecordMapper;
import com.wyz.service.HouseService;
import com.wyz.service.VoteInfoService;
import com.wyz.service.VoteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class VoteRecordServiceImpl extends ServiceImpl<VoteRecordMapper, VoteRecord> implements VoteRecordService {
    @Autowired
    private HouseService houseService;
    @Autowired
    private VoteInfoService voteInfoService;

    @Override
    @Transactional
    public R<String> joinVote(Long id, Integer type) {
        VoteInfo voteInfo = voteInfoService.query().eq("id", id).one();
        if(id==null||type==null||voteInfo==null){
            return R.error("参与无效");
        }
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        House house = houseService.query().eq("user_id", userId).one();
        if(house==null){
            return R.error("您无权参加");
        }
        String houseNum = house.getNum();

        if(voteInfo.getEndTime().isBefore(LocalDate.now())){
            return R.error("投票已结束");
        }

        if(voteInfo.getJoinCode().substring(2,4).equals("00")){
            if (houseNum.startsWith(voteInfo.getJoinCode().substring(0,2))) {
                VoteRecord voteRecord=new VoteRecord();
                voteRecord.setVoteInfoId(id);
                voteRecord.setVoteTime(LocalDateTime.now());
                voteRecord.setUserId(userId);
                voteRecord.setVoteType(type);
                save(voteRecord);
                return R.success("投票成功");
            }
            return R.error("您无权参加");
        }

        if(!houseNum.startsWith(voteInfo.getJoinCode())){
            return R.error("您无权参加");
        }
        VoteRecord voteRecord=new VoteRecord();
        voteRecord.setVoteInfoId(id);
        voteRecord.setVoteTime(LocalDateTime.now());
        voteRecord.setUserId(userId);
        voteRecord.setVoteType(type);
        save(voteRecord);

        return R.success("投票成功");
    }
}
