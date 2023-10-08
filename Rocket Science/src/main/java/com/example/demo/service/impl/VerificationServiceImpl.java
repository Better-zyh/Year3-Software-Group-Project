package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.exception.ApiAsserts;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.mapper.VerificationMapper;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Verification;
import com.example.demo.service.UserService;
import com.example.demo.service.VerificationService;
import com.example.demo.utils.MD5Utils;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;


@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class VerificationServiceImpl extends ServiceImpl<VerificationMapper, Verification> implements VerificationService {
    @Autowired
    private MailServiceImpl mailService;
    @Resource
    private UserService userService;
    @Override
    public Verification generateVerificationCode(String verifyCode, String user_id) {
        // check if there is a user with the same username || email address
        Verification verification = Verification.builder()
                .userId(user_id)
                .verification(verifyCode)
                .createTime(new java.util.Date())
                .build();
        baseMapper.insert(verification);
        return verification;
    }


    @Override
    public Boolean verifyCode(String code, String user_id) {
        return this.baseMapper.verifyCode(code, user_id)!=null;
    }

    @Override
    public Boolean codeOverdue(String user_id) {
        User user = userService.getUserByUserid(user_id);
        Date date = this.baseMapper.codeOverdue(user_id);
        boolean isOverdue = checkDateOverdue(date);
        if (isOverdue){
            this.baseMapper.deleteRecordByUserid(user_id);
            mailService.sendSimpleMail(user.getEmail(),user_id);
        }
        return isOverdue;
    }

    @Override
    public void deleteRecordByUserid(String user_id) {
        baseMapper.deleteRecordByUserid(user_id);
    }

    public boolean checkDateOverdue(Date date){
        return new java.util.Date().getTime()-date.getTime()>=86400000;
    }
}
