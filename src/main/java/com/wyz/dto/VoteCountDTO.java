package com.wyz.dto;

import lombok.Data;

@Data
public class VoteCountDTO {
    private Long totalNum;
    private Long favourNum;
    private Long opponentNum;
    private Long abstentionNum;
}
