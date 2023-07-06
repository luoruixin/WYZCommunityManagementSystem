package com.wyz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.CustomException;
import com.wyz.common.R;
import com.wyz.common.UserHolder;
import com.wyz.dto.UserDTO;
import com.wyz.dto.VoteCountDTO;
import com.wyz.dto.VoteInfoDTO;
import com.wyz.entity.House;
import com.wyz.entity.User;
import com.wyz.entity.VoteInfo;
import com.wyz.entity.VoteRecord;
import com.wyz.mapper.HouseMapper;
import com.wyz.mapper.VoteInfoMapper;
import com.wyz.mapper.VoteRecordMapper;
import com.wyz.service.HouseService;
import com.wyz.service.UserService;
import com.wyz.service.VoteInfoService;
import com.wyz.service.VoteRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VoteInfoServiceImpl extends ServiceImpl<VoteInfoMapper, VoteInfo> implements VoteInfoService {
    @Autowired
    private HouseService houseService;
    @Autowired
    private VoteRecordService voteRecordService;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteRecordMapper voteRecordMapper;
    @Autowired
    private VoteInfoService voteInfoService;
    @Autowired
    private HouseMapper houseMapper;
    @Override
    @Transactional
    public R<String> publish(VoteInfoDTO voteInfoDto) {
        if(StrUtil.isEmpty(voteInfoDto.getContent())
            ||voteInfoDto.getEndTime()==null
            ||StrUtil.isEmpty(voteInfoDto.getTitle())
            ||StrUtil.isEmpty(voteInfoDto.getApart())
            ||StrUtil.isEmpty(voteInfoDto.getCategory())){
            throw new CustomException("请将信息填充完整");
        }
        voteInfoDto.setJoinCode(null);
        String apart = voteInfoDto.getApart();

        String apartNum=new String();
        House house1 = houseService.query().eq("user_id", UserHolder.getUser().getId()).one();
        String areaNum = house1.getNum().substring(0, 2);
        String area = house1.getArea();
        //apartList是业委会成员所在小区的所有楼栋
        List<String> apartList = houseService.query().eq("area", area).list().stream().map(a -> a.getApart()).collect(Collectors.toList());
        if(!apartList.contains(apart)){
            throw new CustomException("您选择的楼栋无效");
        }
        if(!apart.contains("全部")){
            House house = houseService.query().eq("apart", apart).last("limit 1").one();
            if(house==null){
                throw new CustomException("您选择的楼栋无效");
            }
            String num = house.getNum();
            if(StrUtil.isEmpty(num)){
                throw new CustomException("发布无效");
            }
            apartNum = num.substring(2, 4);
        }else {
            apartNum="00";
        }
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
        page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    @Override
    public R<String> deleteVote(Long id) {
        if(id==null){
            throw new CustomException("删除无效");
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
            throw new CustomException("无效");
        }
        VoteInfo voteInfo = query().eq("id", id).one();
        VoteInfoDTO voteInfoDTO=new VoteInfoDTO();
        BeanUtil.copyProperties(voteInfo,voteInfoDTO);
        Long createUserId = voteInfo.getCreateUserId();
        User user = userService.getById(createUserId);
        voteInfoDTO.setName(user.getName());
        return R.success(voteInfoDTO);
    }

    @Override
    @Transactional
    public R<VoteCountDTO> countVote(Long id) {
        if(id==null){
            throw new CustomException("id为空");
        }
        VoteInfo voteInfo = query().eq("id", id).one();
        if(voteInfo==null){
            throw new CustomException("投票不存在");
        }
        String joinCode = voteInfo.getJoinCode();
        //查询楼栋总人数，未参与投票则视为弃权
        Long totalNum = Long.valueOf(houseService.query().likeRight("num", joinCode).isNotNull("user_id").count());
        Long favourNum = voteRecordMapper.getFavourNum(id);
        Long opponentNum=voteRecordMapper.getOpponentNum(id);
        Long abstentionNum=totalNum-favourNum-opponentNum;
        Double favourRatio=favourNum.doubleValue()/totalNum;
        Double opponentRatio=opponentNum.doubleValue()/totalNum;
        Double abstentionRatio=abstentionNum.doubleValue()/totalNum;

        VoteCountDTO voteCountDTO=new VoteCountDTO();
        voteCountDTO.setTotalNum(totalNum);
        voteCountDTO.setFavourNum(favourNum);
        voteCountDTO.setOpponentNum(opponentNum);
        voteCountDTO.setAbstentionNum(abstentionNum);
        voteCountDTO.setFavourRatio(favourRatio);
        voteCountDTO.setOpponentRatio(opponentRatio);
        voteCountDTO.setAbstentionRatio(abstentionRatio);
        return R.success(voteCountDTO);
    }

    @Override
    @Transactional
    public R<List<String>> getAparts() {
        Long id = UserHolder.getUser().getId();
        String area = houseService.query().eq("user_id", id).one().getArea();
        List<String> apartList = houseService.query().eq("area", area).list().stream().map(a -> a.getApart()).collect(Collectors.toList());
        // 使用Set去重
        Set<String> uniqueSet = new HashSet<>(apartList);

        // 将去重后的元素转换回List（可选）
        List<String> distinctList = new ArrayList<>(uniqueSet);
        return R.success(distinctList);
    }
}
