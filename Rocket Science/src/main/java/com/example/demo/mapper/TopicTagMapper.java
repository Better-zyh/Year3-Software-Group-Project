package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.model.entity.TopicTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.vo.PostVO;


import java.util.Set;


@Repository
public interface TopicTagMapper extends BaseMapper<TopicTag> {
    /**
     * 根据标签获取话题ID集合
     *
     * @param id
     * @return
     */
    Set<String> getTopicIdsByTagId(@Param("id") String id);

    Page<PostVO> searchByKey(@Param("page") Page<PostVO> page, @Param("keyword") String keyword);
}
