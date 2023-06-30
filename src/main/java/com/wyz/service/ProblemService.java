package com.wyz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.entity.Problem;

public interface ProblemService extends IService<Problem> {
    R<String> report(Problem problem);

    R<Page> pageMe(int page, int pageSize);

    R<String> updateProblem(Problem problem);

    R<String> deleteProblem(Long id);

    R<Page> pageAll(int page, int pageSize, String condition);
}
