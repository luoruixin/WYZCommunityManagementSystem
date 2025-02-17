package com.wyz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.service.VoteRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteRecordService voteRecordService;

    //查询现在能参加的投票
    @GetMapping("/pageCan")
    public R<Page> pageCan(int page,int pageSize){
        return voteRecordService.pageCan(page,pageSize);
    }

    //参与投票
    @PostMapping("/joinVote")
    @CacheEvict(value = "voteCache:pageMe",allEntries = true)
    public R<String> joinVote(@RequestParam("id") Long id,@RequestParam("type") Integer type){
        return voteRecordService.joinVote(id,type);
    }

    //查看自己参加过的投票
    @GetMapping("/pageMe")
    @Cacheable(value = "voteCache:pageMe",key = "#page+'-'+#pageSize+'-'+T(java.lang.String).valueOf(T(com.wyz.common.UserHolder).getUser().getId())")
    public R<Page> pageMe(int page,int pageSize){
        return voteRecordService.pageMe(page,pageSize);
    }
}
