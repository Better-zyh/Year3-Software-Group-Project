package com.example.demo.controller;

import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.User;
import com.example.demo.model.vo.CommentVO;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.example.demo.jwt.JwtUtil.USER_NAME;
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private UserService umsUserService;

    @GetMapping("/get_comments")
    public ApiResult<List<CommentVO>> getCommentsByTopicID(@RequestParam(value = "topicid", defaultValue = "1") String topicid) {
        List<CommentVO> lstComment = commentService.getCommentsByTopicID(topicid);
        return ApiResult.success(lstComment);
    }
    @PostMapping("/add_comment")
    public ApiResult<Comment> add_comment(@RequestHeader(value = USER_NAME) String userName,
                                          @RequestBody CommentDTO dto) {
        if (umsUserService.isActive(userName)&&umsUserService.isFrozen(userName)){
            User user = umsUserService.getUserByUsername(userName);
            Comment comment = commentService.create(dto, user);
            return ApiResult.success(comment);
        }
        return ApiResult.failed("Account is not active or is frozen, Can not comment");
    }
    @PostMapping("/delete_comment")
    public ApiResult<String> delete_comment(@RequestHeader(value = USER_NAME) String userName, @RequestParam("comment_id") String commentId) {
        if (umsUserService.isActive(userName)&&umsUserService.isFrozen(userName)){
            User user = umsUserService.getUserByUsername(userName);
            commentService.delete(user.getId(), commentId);
            return ApiResult.success("Delete Successfully");
        }
        return ApiResult.failed("Account is not active or is frozen, Can not comment");
    }
}
