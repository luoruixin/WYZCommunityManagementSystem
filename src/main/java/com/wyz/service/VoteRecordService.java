package com.wyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.entity.VoteRecord;

public interface VoteRecordService extends IService<VoteRecord> {
    R<String> joinVote(Long id, Integer type);
}
