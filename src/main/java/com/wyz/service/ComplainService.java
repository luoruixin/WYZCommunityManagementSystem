package com.wyz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.dto.ComplainDTO;
import com.wyz.entity.Car;
import com.wyz.entity.Complain;

public interface ComplainService extends IService<Complain> {
    R<String> addComplain(Complain complain);

    R<Page> pageMe(int page, int pageSize);

    R<Page> pageAll(int page, int pageSize, String condition);

    R<ComplainDTO> getDetail(Long id);
}
