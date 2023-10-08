package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper extends BaseMapper<User> {
    //验证密码
    public Object verifyPassword(@Param("username") String username, @Param("password") String password);

    Boolean isActive(@Param("userName")String userName);
    Boolean isFrozen(@Param("userName")String userName);
    void activateUserByUsername(@Param("user_id")String user_id);

    void freeze(@Param("user_id") String user_id);
}
