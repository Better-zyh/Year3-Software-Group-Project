package com.example.demo.controller;

import com.example.demo.common.api.ApiResult;
import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.VerificationService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


import static com.example.demo.jwt.JwtUtil.USER_NAME;

@RestController
public class VerifyController extends BaseController{

    @Resource
    private VerificationService verificationService;
    @Resource
    private UserService userService;


    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public ApiResult<String> VerifyCode(@RequestHeader(value = USER_NAME) String userName, @RequestParam("code") String code){
        User user = userService.getUserByUsername(userName); //Get the user by name
        if (!verificationService.codeOverdue(user.getId())){
            if (verificationService.verifyCode(code, user.getId())){
                userService.activateUserByUsername(user.getId());
                verificationService.deleteRecordByUserid(user.getId());
                return ApiResult.success("Validation succesfully. Now you can post your ideas");
            }
        }
        return ApiResult.failed("Your verification code has expired, we send a new email to you");

    }
}
