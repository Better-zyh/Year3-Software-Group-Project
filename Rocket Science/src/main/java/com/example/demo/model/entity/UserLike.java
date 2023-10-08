package com.example.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@TableName("bms_post_user_like")
@AllArgsConstructor
@NoArgsConstructor
public class UserLike implements Serializable {
    @TableId(value="id",type = IdType.ASSIGN_ID)
    private String id;

    @TableField("post_id")
    private String postId;

    @TableField("user_id")
    private String userId;
}
