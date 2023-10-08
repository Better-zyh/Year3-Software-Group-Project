package com.example.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.demo.mapper.TagMapper;
import com.example.demo.mapper.TopicMapper;
import com.example.demo.mapper.UserLikeMapper;
import com.example.demo.mapper.UserMapper;

import com.example.demo.model.dto.CreateTopicDTO;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.Tag;
import com.example.demo.model.entity.TopicTag;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.UserLike;
import com.example.demo.model.vo.PostVO;
import com.example.demo.model.vo.ProfileVO;
import com.example.demo.service.PostService;

import com.example.demo.service.TagService;
import com.example.demo.service.TopicTagService;

import com.example.demo.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl extends ServiceImpl<TopicMapper, Post> implements PostService {
    @Resource
    private TagMapper tagMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    @Lazy
    private TagService tagService;

    @Autowired
    private UserService userService;
    @Resource
    private UserLikeMapper userLikeMapper;

    @Autowired
    private TopicTagService TopicTagService;

    @Override
    public Map<String, Object> viewTopic(String id, String username) {
        Map<String, Object> map = new HashMap<>(16);
        Post topic = this.baseMapper.selectById(id);
        org.springframework.util.Assert.notNull(topic, "当前话题不存在,或已被作者删除");
        // 查询话题详情
        topic.setView(topic.getView() + 1);
        this.baseMapper.updateById(topic);
        // emoji转码
        topic.setContent(EmojiParser.parseToUnicode(topic.getContent()));
        map.put("topic", topic);
        // 标签
        QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TopicTag::getTopicId, topic.getId());
        Set<String> set = new HashSet<>();
        for (TopicTag articleTag : TopicTagService.list(wrapper)) {
            set.add(articleTag.getTagId());
        }
        List<Tag> tags = tagService.listByIds(set);
        map.put("tags", tags);
        // 作者
        ProfileVO user = userService.getUserProfile(topic.getUserId());
        map.put("user", user);
        if (username == null) map.put("liked",false);
        else map.put("liked",judgeLikeExistByUsername(username, id));
        return map;
    }

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        // 查询话题
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        // 查询话题的标签
        iPage.getRecords().forEach(topic -> {
            List<TopicTag> topicTags = TopicTagService.selectByTopicId(topic.getId()); //根据id获取标签集合
            if (!topicTags.isEmpty()) {
                List<String> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
                List<Tag> tags = tagMapper.selectBatchIds(tagIds); //mybatis自带的函数,根据传入的id集合,获取标签对象
                topic.setTags(tags);
            }
        });
        return iPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Post create(CreateTopicDTO dto , User user){
        Post topic1 = this.baseMapper.selectOne(new LambdaQueryWrapper<Post>().eq(Post::getTitle, dto.getTitle()));
        Assert.isNull(topic1,"Topic existed, please revise");
        //encapsulation
        Post topic = Post.builder()
                .userId(user.getId())
                .title(dto.getTitle())
                .content(EmojiParser.parseToAliases(dto.getContent()))
                .createTime(new Date())
                .build();
        this.baseMapper.insert(topic);

        // score increases
        int newScore = user.getScore() + 1;
        userMapper.updateById(user.setScore(newScore));

        // Tag
        if (!ObjectUtils.isEmpty(dto.getTags())) {
            // Keep tag
            List<Tag> tags = tagService.insertTags(dto.getTags());
            TopicTagService.createTopicTag(topic.getId(), tags);
        }

        return topic;
    }

    @Override
    public Page<PostVO> searchByKeyword(String keyword, Page<PostVO> page) {
        Page<PostVO> iPage = this.baseMapper.searchByKeyword(page, keyword);
        setTopicTags(iPage);
        return iPage;
    }

    @Override
    public Page<PostVO> searchByTag(String tag, Page<PostVO> page) {
        Page<PostVO> iPage = this.baseMapper.searchByTag(page, tag);
        setTopicTags(iPage);
        return iPage;
    }

    @Override
    public Page<PostVO> searchByUser(String userId, Page<PostVO> page) {
        Page<PostVO> iPage = this.baseMapper.searchByUser(page, userId);
        return  iPage;
    }

    @Override
    public Page<PostVO> searchByLiked(String userId, Page<PostVO> page) {
        Page<PostVO> iPage = this.baseMapper.searchByLiked(page, userId);
        return  iPage;
    }

    private void setTopicTags(Page<PostVO> iPage) {
        iPage.getRecords().forEach(topic -> {
            List<TopicTag> topicTags = TopicTagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
                List<String> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
                List<Tag> tags = tagMapper.selectBatchIds(tagIds);
                topic.setTags(tags);
            }
        });
    }

    @Override
    public boolean setlike(User user, Post post){
        boolean flag = judgeLikeExist(user.getId(),post.getId()); //if flag= true
        if(!flag){//Already liked
            post.setLikes(post.getLikes()+1);
            this.baseMapper.updateById(post);
            UserLike userLike = UserLike.builder()
                    .userId(user.getId())
                    .postId(post.getId())
                    .build();
            userLikeMapper.insert(userLike);
            return true;
        }else{//Have not liked
            return false;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelLike(User user, Post post){
        post.setLikes(post.getLikes()-1);
        this.baseMapper.updateById(post);
        this.baseMapper.cancelLike(user.getId(), post.getId());
    }

    @Override
    public boolean judgeLikeExist(String userid, String postid) {
        int res = this.baseMapper.judgeLikeExist(userid, postid);
        return res != 0; //如果不为0，意味着点过赞，返回true
    }

    @Override
    public boolean judgeLikeExistByUsername(String username, String postid) {
        int res = this.baseMapper.judgeLikeExistByUsername(username, postid);
        return res != 0; //如果不为0，意味着点过赞，返回true
    }

}
