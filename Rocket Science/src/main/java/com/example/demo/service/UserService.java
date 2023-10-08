package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.dto.LoginDTO;
import com.example.demo.model.dto.RegisterDTO;
import com.example.demo.model.entity.User;
import com.example.demo.model.vo.ProfileVO;



public interface UserService extends IService<User> {

    /**
     * Register function
     *
     * @param dto
     * @return A user object
     */
    User executeRegister(RegisterDTO dto);

    /**
     * Get user's information
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);
    User getUserByUserid(String user_id);
    /**
     * User login
     *
     * @param dto
     * @return 生成的JWT的token Generate token with JWT
     */
    String executeLogin(LoginDTO dto);

    //验证密码
    boolean verifyPassword(String username, String password);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    ProfileVO getUserProfile(String id);
    void uploadAvatar(User user, String url);

    void updatePassword(User user, String new_password);
    void updateAlias(User user, String new_alias);
    void updateAvatar(User user, String new_avatar);
    Boolean isActive(String userName);
    Boolean isFrozen(String userName);
    void activateUserByUsername(String userName);
    void freeze(String user_id);
}
