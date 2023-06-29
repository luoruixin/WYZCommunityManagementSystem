package com.wyz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.VoteInfoDTO;
import com.wyz.entity.VoteInfo;
import com.wyz.mapper.VoteInfoMapper;
import com.wyz.service.HouseService;
import com.wyz.service.VoteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class VoteInfoServiceImpl extends ServiceImpl<VoteInfoMapper, VoteInfo> implements VoteInfoService {
    @Autowired
    private HouseService houseService;
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
        String num = houseService.query().eq("apart", apart).one().getNum();
        String apartNum = num.substring(2, 4);
        String areaNum = houseService.query().eq("user_id", UserHolder.getUser().getId()).one().getNum().substring(0, 2);
        voteInfoDto.setJoinCode(areaNum+apartNum);
        voteInfoDto.setCreateUserId(UserHolder.getUser().getId());
        voteInfoDto.setStartTime(LocalDate.now());

        VoteInfo voteInfo=new VoteInfo();
        BeanUtil.copyProperties(voteInfoDto,voteInfo);
        save(voteInfo);
        return R.success("发布成功");
    }
}
