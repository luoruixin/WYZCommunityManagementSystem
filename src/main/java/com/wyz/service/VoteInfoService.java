package com.wyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.dto.VoteInfoDTO;
import com.wyz.entity.VoteInfo;

public interface VoteInfoService extends IService<VoteInfo> {
    R<String> publish(VoteInfoDTO voteInfo);
}
