package com.example.demo.domain;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
    private String id;
    private String avatar;
    private String email;
    private String mobile;
    private Integer score;
    private String token;
    private String bio;
    private Boolean active;
    private Boolean status;
    private Integer role_id;
    private Date create_time;
    private Date modify_time;
    private String username;
    private String alias;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Date getCreat_time() {
        return create_time;
    }

    public void setCreat_time(Date creat_time) {
        this.create_time = creat_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", score=" + score +
                ", token='" + token + '\'' +
                ", bio='" + bio + '\'' +
                ", active=" + active +
                ", status=" + status +
                ", role_id=" + role_id +
                ", create_time=" + create_time +
                ", modify_time=" + modify_time +
                ", username='" + username + '\'' +
                ", alias='" + alias + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
