package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.dto.CreateTopicDTO;
import com.example.demo.model.entity.Post;

import com.example.demo.model.entity.User;
import com.example.demo.model.vo.PostVO;

import java.util.Map;


public interface PostService extends IService<Post> {

    /**
     * 获取首页话题列表
     *
     * @param page
     * @param tab
     * @return 帖子的vo对象
     */
    Page<PostVO> getList(Page<PostVO> page, String tab);

    /**
     * Post a post
     * @param dto
     * @param principal
     * @return
     */
    Post create(CreateTopicDTO dto, User principal);

    Map<String, Object> viewTopic(String id, String username);

    Page<PostVO> searchByKeyword(String keyword, Page<PostVO> page);

    Page<PostVO> searchByTag(String tag, Page<PostVO> page);
    Page<PostVO> searchByUser(String userId, Page<PostVO> page);
    Page<PostVO> searchByLiked(String userId, Page<PostVO> page);
    /**
     * Post a post
     * @param user
     * @param post
     * @return
     */
    boolean setlike(User user, Post post);

    void cancelLike(User user_id, Post post_id);
    //验证是否已经点赞
    boolean judgeLikeExist(String username, String password);

    boolean judgeLikeExistByUsername(String userid, String postid);
}
