package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.entity.Verification;
import org.apache.ibatis.annotations.Param;


public interface VerificationService extends IService<Verification> {

    /**
     *
     * @param user_id 用户id
     * @return 是否已经成功在数据库中添加用户的邮箱验证码
     */
    Verification generateVerificationCode(String verifyCode, String user_id);

    /**
     *
     * @param code  验证码
     * @param user_id 用户id
     * @return  用户输入的验证码是否正确
     */
    Boolean verifyCode(String code, String user_id);

    /**
     *
     * @param user_id 用户id
     * @return 根据用户名，返回用户的验证码是否已经过期
     */
    Boolean codeOverdue(String user_id);
    void deleteRecordByUserid( String user_id);
}
