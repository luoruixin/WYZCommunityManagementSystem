package com.wyz.committeeController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.dto.VoteInfoDTO;
import com.wyz.entity.VoteInfo;
import com.wyz.service.VoteInfoService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/committeeVote")
public class CommitteeVoteController {
    @Autowired
    private VoteInfoService voteInfoService;

    //发布投票
    @PostMapping("/publish")
    public R<String> publish(@RequestBody VoteInfoDTO voteInfoDTO){
        try {
            return voteInfoService.publish(voteInfoDTO);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("网络繁忙，请重试");
        }
    }

    //投票分页查询
    @GetMapping("/page")
    @Cacheable(value = "committeeVoteCache",key = "#root.methodName",condition = "#result!=null")
    public R<Page> pageR(int page,int pageSize,String condition){
        R<Page> r = voteInfoService.pageR(page, pageSize, condition);
        return r;
    }

    //查询自己发布的投票
    @GetMapping("/pageMe")
    public R<Page> pageMe(int page,int pageSize,String condition){
        return voteInfoService.pageMe(page,pageSize,condition);
    }

    //删除投票
    @DeleteMapping("/delete")
    public R<String> delete(@RequestParam("id")Long id){
        return voteInfoService.deleteVote(id);
    }

    //查看投票详情
    // TODO: 添加分析投票的功能
    @GetMapping("/details")
    public R<VoteInfoDTO> details(@RequestParam("id")Long id){
        return voteInfoService.getDetails(id);
    }


}
