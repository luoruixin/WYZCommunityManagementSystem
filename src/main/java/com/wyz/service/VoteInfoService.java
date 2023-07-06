package com.wyz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.dto.VoteCountDTO;
import com.wyz.dto.VoteInfoDTO;
import com.wyz.entity.VoteInfo;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface VoteInfoService extends IService<VoteInfo> {
    R<String> publish(VoteInfoDTO voteInfo);

    R<Page> pageR(int page, int pageSize, String condition);

    R<Page> pageMe(int page, int pageSize, String condition);

    R<String> deleteVote(Long id);

    R<VoteInfoDTO> getDetails(Long id);

    R<VoteCountDTO> countVote(Long id);

    R<List<String>> getAparts();
}
