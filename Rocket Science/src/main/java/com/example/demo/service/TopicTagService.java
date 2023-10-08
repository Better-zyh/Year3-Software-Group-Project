package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.entity.Tag;
import com.example.demo.model.entity.TopicTag;


import java.util.List;
import java.util.Set;

public interface TopicTagService extends IService<TopicTag> {

    /**
     * 获取Topic Tag 关联记录
     *
     * @param topicId TopicId
     * @return TopicTag对象
     */
    List<TopicTag> selectByTopicId(String topicId);

    /**
     * 获取标签换脸话题ID集合
     *
     * @param id
     * @return id集合（现在还没用到）
     */
    Set<String> selectTopicIdsByTagId(String id);

    /**
     * 创建中间关系 Create middle relationship
     *
     * @param id
     * @param tags
     * @return
     */
    void createTopicTag(String id, List<Tag> tags);
}
