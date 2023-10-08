package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    List<Tag> insertTags(List<String> tags);

    Page<Post> selectTopicsByTagId(Page<Post> topicPage, String id);

    void deleteTagInfoByPostId(String post_id);
}
