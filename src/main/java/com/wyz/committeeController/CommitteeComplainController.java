package com.wyz.committeeController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyz.common.R;
import com.wyz.dto.ComplainDTO;
import com.wyz.service.ComplainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/CommitteeComplain")
public class CommitteeComplainController {
    @Autowired
    private ComplainService complainService;
    @GetMapping("/pageAll")
    @Cacheable(value = "CommitteeComplain:pageAll",key = "#page+'-'+#pageSize",condition = "#condition==null")
    public R<Page> pageAll(int page,int pageSize,String condition){
        return complainService.pageAll(page,pageSize,condition);
    }

    @GetMapping("/detail")
    @Cacheable(value = "CommitteeComplain:getDetail",key = "#id")
    public R<ComplainDTO> getDetail(@RequestParam("id")Long id){
        return complainService.getDetail(id);
    }
}
