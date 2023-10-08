package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import com.example.demo.model.vo.PostVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicMapper extends BaseMapper<Post> {
    /**
     * 分页查询首页话题列表
     * <p>
     *
     * @param page
     * @param tab
     * @return
     */
    Page<PostVO> selectListAndPage(@Param("page") Page<PostVO> page, @Param("tab") String tab);

    Page<PostVO> searchByKeyword(@Param("page") Page<PostVO> page, @Param("keyword") String keyword);

    Page<PostVO> searchByTag(@Param("page") Page<PostVO> page, @Param("tag") String tag);

    Page<PostVO> searchByUser(@Param("page") Page<PostVO> page, @Param("user_id") String user_id);

    void cancelLike(@Param("userid")String user,@Param("postid") String post);

    Integer judgeLikeExist(@Param("user_id") String user_id, @Param("post_id") String post_id);

    Integer judgeLikeExistByUsername(@Param("user_name")String username, @Param("post_id")String post_id);

    Page<PostVO> searchByLiked(@Param("page") Page<PostVO> page,  @Param("user_id")String userId);
}
