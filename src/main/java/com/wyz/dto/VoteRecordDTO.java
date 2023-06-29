package com.wyz.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VoteRecordDTO {
    private Long id;
    private Long userId;
    private Long voteInfoId;
    private LocalDateTime voteTime;
    private Integer voteType;

    private LocalDate startTime;
    private LocalDate endTime;
    private String category;
    private String content;
    private String title;
}
