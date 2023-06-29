package com.wyz.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VoteInfoDTO {
    private Long id;
    private Long createUserId;
    private LocalDate startTime;
    private LocalDate endTime;
    private String category;
    private String content;
    private String joinCode;
    private String title;
    private String apart;
}
