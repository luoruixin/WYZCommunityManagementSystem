package com.wyz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.entity.User;
import com.wyz.entity.UserRecord;
import com.wyz.mapper.UserMapper;
import com.wyz.mapper.UserRecordMapper;
import com.wyz.service.UserRecordService;
import com.wyz.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserRecordServiceImpl extends ServiceImpl<UserRecordMapper, UserRecord> implements UserRecordService {
}
