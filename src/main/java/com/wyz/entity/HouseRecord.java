package com.wyz.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("house_record")
public class HouseRecord implements Serializable {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "house_id")
    private Long houseId;
    @TableField(value = "name")
    private String name;
    @TableField(value = "id_card")
    private String idCard;
    @TableField(value = "start_time")
    private LocalDateTime startTime;
    @TableField(value = "end_time")
    private LocalDateTime endTime;
}
