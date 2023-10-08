package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.demo.model.entity.Tag;
import org.springframework.stereotype.Repository;


@Repository
public interface TagMapper extends BaseMapper<Tag> {

    void deleteTagInfoByPostId(String post_id);
}
