package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.entity.Verification;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;


@Repository
public interface VerificationMapper extends BaseMapper<Verification> {

    Object verifyCode(@Param("code")String code, @Param("user_id")String user_id);

    Date codeOverdue(@Param("user_id")String user_id);

    void deleteRecordByUserid(@Param("user_id")String user_id);
}
