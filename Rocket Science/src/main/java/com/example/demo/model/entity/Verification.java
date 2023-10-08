package com.example.demo.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@TableName("bms_verification")
@AllArgsConstructor
@NoArgsConstructor
public class Verification implements Serializable {
    @TableId(value="id",type = IdType.ASSIGN_ID)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("verification")
    private String verification;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
}
