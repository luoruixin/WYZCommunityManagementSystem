package com.wyz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("dictionary")
public class Dictionary {
    /**
     * id标识
     */
    @TableId(value = "id")
    private Long id;

    @TableField(value = "name")
    private String name;

    /**
     * 数据字典项--json格式
     [{
     "sd_name": "低级",
     "sd_id": "200001",
     "sd_status": "1"
     }, {
     "sd_name": "中级",
     "sd_id": "200002",
     "sd_status": "1"
     }, {
     "sd_name": "高级",
     "sd_id": "200003",
     "sd_status": "1"
     }]
     */
    @TableField(value = "item_values")
    private String itemValues;

}
