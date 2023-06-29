package com.wyz.committeeController;

import com.wyz.common.R;
import com.wyz.dto.VoteInfoDTO;
import com.wyz.entity.VoteInfo;
import com.wyz.service.VoteInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/committeeVote")
public class CommitteeVoteController {
    @Autowired
    private VoteInfoService voteInfoService;
    //TODO:发布投票(未完成)
    @PostMapping("/publish")
    public R<String> publish(@RequestBody VoteInfoDTO voteInfoDTO){
        try {
            return voteInfoService.publish(voteInfoDTO);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("网络繁忙，请重试");
        }
    }


}
