package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.exception.ApiAsserts;

import com.example.demo.jwt.JwtUtil;
import com.example.demo.mapper.FollowMapper;
import com.example.demo.mapper.TopicMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.LoginDTO;
import com.example.demo.model.dto.RegisterDTO;
import com.example.demo.model.dto.UpdateDTO;
import com.example.demo.model.entity.Follow;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import com.example.demo.model.vo.ProfileVO;
import com.example.demo.service.UserService;
import com.example.demo.utils.MD5Utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.util.Date;



@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FollowMapper followMapper;

    @Override
    public boolean verifyPassword(String username, String password) {
        return userMapper.verifyPassword(username,password) != null;
    }

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    @Override
    public ProfileVO getUserProfile(String id) {
        ProfileVO profile = new ProfileVO();
        User user = baseMapper.selectById(id);
        BeanUtils.copyProperties(user, profile);
        // 用户文章数
        int count = topicMapper.selectCount(new LambdaQueryWrapper<Post>().eq(Post::getUserId, id));
        profile.setTopicCount(count);

        // 粉丝数
        int followers = followMapper.selectCount((new LambdaQueryWrapper<Follow>().eq(Follow::getParentId, id)));
        profile.setFollowerCount(followers);
        return profile;
    }

    @Override
    public void uploadAvatar(User user, String url) {
            user.setAvatar(url);
            baseMapper.updateById(user);
    }

    @Override
    public void updatePassword(User user, String new_password) {
        user.setPassword(MD5Utils.getPwd(new_password));
        baseMapper.updateById(user);
    }

    @Override
    public void updateAlias(User user, String new_alias) {
        user.setAlias(new_alias);
        baseMapper.updateById(user);
    }

    @Override
    public void updateAvatar(User user, String new_avatar) {
        user.setAvatar(new_avatar);
        baseMapper.updateById(user);
    }

    @Override
    public Boolean isActive(String userName) {
        return this.baseMapper.isActive(userName);
    }

    @Override
    public Boolean isFrozen(String userName) {
        return this.baseMapper.isFrozen(userName);
    }

    @Override
    public void activateUserByUsername(String userName) {
        this.baseMapper.activateUserByUsername(userName);
    }

    @Override
    public void freeze(String user_id) {
        this.baseMapper.freeze(user_id);
    }

//    @Override
//    public void updateUserInfo(User user,UpdateDTO dto) {
//        if(dto.getNew_password()!=null){
//            user.setPassword(MD5Utils.getPwd(dto.getNew_password()));
//        }
//        if(dto.getAvatar()!=null){
//            user.setAvatar(dto.getAvatar());
//        }
//        if(dto.getAlias()!=null){
//            user.setAlias(dto.getAlias());
//        }
//        baseMapper.updateById(user);
//    }

    @Override
    public User executeRegister(RegisterDTO dto) {
        // check if there is a user with the same username || email address
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername()).or().eq(User::getEmail, dto.getEmail());
        User user = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(user)) {
            ApiAsserts.fail("Email existed!");
        }
        User addUser = User.builder()
                .username(dto.getUsername())
                .alias(dto.getUsername())
                .password(MD5Utils.getPwd(dto.getPassword()))
                .email(dto.getEmail())
                .createTime(new Date())
                .status(true)
                .build();
        if(dto.getEmail().contains("student")){
            addUser.setBio("学生");
        }else{
            addUser.setBio("老师");
        }
        baseMapper.insert(addUser);
        return addUser;
    }
    @Override
    public User getUserByUsername(String username){
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }
    @Override
    public User getUserByUserid(String user_id){
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, user_id));
    }
    @Override
    public String executeLogin(LoginDTO dto) {
        String token = null;
        try {
            User user = getUserByUsername(dto.getUsername());
            String encodePwd = dto.getPassword();
            if(!encodePwd.equals(user.getPassword()))
            {
                throw new Exception("Password is wrong");
            }
            token = JwtUtil.generateToken(String.valueOf(user.getUsername()));
        } catch (Exception e) {
            log.warn("Account not exist or Password verification failed=======>{}", dto.getUsername());
        }
        return token;
    }

}
