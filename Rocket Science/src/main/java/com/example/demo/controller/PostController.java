package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.CreateTopicDTO;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import com.example.demo.model.vo.PostVO;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import com.example.demo.service.TagService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

import static com.example.demo.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/post")
public class PostController extends BaseController {

    @Resource //注入service
    private PostService postService;

    @Resource
    private UserService userService;

    @Resource //注入service
    private TagService tagService;

    @Resource //注入service
    private CommentService commentService;

    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id, @RequestParam("username") String username) {
        Map<String, Object> map = postService.viewTopic(id, username);
        return ApiResult.success(map);
    }

    @GetMapping("/list") //获取帖子列表，所以method用GET
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, //第几页
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) { //第几条{
        Page<PostVO> list = postService.getList(new Page<>(pageNo, pageSize), tab); //调用Postservice里的getlist方法
        return ApiResult.success(list);
    }


    @RequestMapping(value ="/create", method =RequestMethod.POST)
    public ApiResult<Post> create(@RequestHeader(value = USER_NAME) String userName, @RequestBody CreateTopicDTO dto){
        if (userService.isActive(userName)&&userService.isFrozen(userName)){
            User user = userService.getUserByUsername(userName); //Get the user by name
            Post topic = postService.create(dto,user); //Create a post by post_service
            return ApiResult.success(topic); //Return the post
        }
        return ApiResult.failed("Account is not active or is frozen, can not make a post");
    }

    @RequestMapping(value = "/setlike/{id}")
    public ApiResult<String> setLike(@RequestHeader(value = USER_NAME) String userName, @PathVariable("id") String id){
        //Get this post
        User user = userService.getUserByUsername(userName); //Get the user by name
        Post byId = postService.getById(id); // Get the post by post id
        boolean flag= postService.setlike(user, byId);
        if(flag){
            return ApiResult.success("Like Successfully");
        }else{
            return ApiResult.failed("you like it already");
        }
    }

    @RequestMapping(value = "/cancelLike/{id}")
    public ApiResult<String> cancelLike(@RequestHeader(value = USER_NAME) String userName, @PathVariable("id") String id){
        //Get this post
        User user = userService.getUserByUsername(userName); //Get the user by name
        Post byId = postService.getById(id); // Get the post by post id
        postService.cancelLike(user, byId);
        return ApiResult.success("Cancel Like Successfully"); //Return the post
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(@RequestHeader(value = USER_NAME) String userName, @PathVariable("id") String id) {

        User umsUser = userService.getUserByUsername(userName);
        Post byId = postService.getById(id);
        if (byId == null){
            return ApiResult.failed("Topic has already deleted");
        }
        if (!byId.getUserId().equals(umsUser.getId()) && !"admin".equals(umsUser.getId())){
            return ApiResult.failed("You have no permission to delete other's post");
        }
        postService.removeById(id);
        tagService.deleteTagInfoByPostId(id);
        commentService.deleteCommentByPostId(id);
        return ApiResult.success(null,"Delete successfully");
    }

}
