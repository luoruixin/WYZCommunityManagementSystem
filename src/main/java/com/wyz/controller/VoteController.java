package com.wyz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.service.VoteRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteRecordService voteRecordService;

    //查询现在正在进行的投票

    //参与投票
    @PostMapping("/joinVote")
    public R<String> joinVote(@RequestParam("id") Long id,@RequestParam("type") Integer type){
        return voteRecordService.joinVote(id,type);
    }

    //查看自己参加过的投票
    @GetMapping("/pageMe")
    public R<Page> pageMe(int page,int pageSize,String condition){
        return voteRecordService.pageMe(page,pageSize,condition);
    }
}
