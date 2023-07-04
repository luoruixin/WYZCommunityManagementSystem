package com.wyz.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wyz.entity.Complain;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

@Data
public class ComplainDTO extends Complain {
    @TableField(value = "name")
    private String name;
    @TableField(value = "id_card")
    private String idCard;
    @TableField(value = "phone")
    private String phone;
}
