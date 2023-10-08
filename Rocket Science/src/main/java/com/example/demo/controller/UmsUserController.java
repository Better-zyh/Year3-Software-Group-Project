package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.api.ApiResult;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.model.dto.UpdateDTO;
import com.example.demo.model.vo.PostVO;
import com.example.demo.service.MailService;
import com.example.demo.service.PostService;
import com.example.demo.utils.MD5Utils;
import com.example.demo.model.dto.RegisterDTO;
import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.Consts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

import static com.example.demo.jwt.JwtUtil.USER_NAME;

@Slf4j
@RestController
@RequestMapping("/user")
public class UmsUserController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private MailService mailService;
    @Resource
    private PostService postService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        User user = userService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("Register failed");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        mailService.sendSimpleMail(dto.getEmail(), user.getId());
        return ApiResult.success(map);
    }
//    @RequestMapping(value = "/mail", method = RequestMethod.POST)
//    public ApiResult<String> mail(@RequestBody String email) {
//        mailService.sendSimpleMail(email);
//        return ApiResult.success("have sent a mail");
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest request, HttpSession session) {//需要从前端获取账号密码数据；登录成功要把用户信息放到session中。
        String token = null;
        JSONObject jsonObject = new JSONObject();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        password = MD5Utils.getPwd(password);
        boolean flag = userService.verifyPassword(name, password);
        token = JwtUtil.generateToken(String.valueOf(name)); //以用户名生成jwt的token
        if (flag) {
            jsonObject.put(Consts.CODE, 1); //键值对 键是Const类中定义好的常量
            jsonObject.put(Consts.MSG, "Login successfully");
            jsonObject.put("token", token);
            jsonObject.put("username", name);
            session.setAttribute(Consts.NAME, name); //键值对 键是Const类中定义好的常量
            session.setAttribute(Consts.PASSWORD, password);
        } else {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "Username or password is wrong");
            jsonObject.put("token", token);
            jsonObject.put("username", null);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiResult<String> update(@RequestHeader(value = USER_NAME) String userName, @RequestBody UpdateDTO dto) {
        User user_to_be_updated = userService.getUserByUsername(userName);
        if (!dto.getAlias().equals("null")) {
            userService.updateAlias(user_to_be_updated, dto.getAlias());
            return ApiResult.success("update alias success");
        }
        if (!dto.getAvatar().equals("null")) {
            userService.updateAvatar(user_to_be_updated, dto.getAvatar());
            return ApiResult.success("update avatar success");
        }
        if (!dto.getOld_password().equals("null")) {
            boolean flag = user_to_be_updated.getPassword().equals(MD5Utils.getPwd(dto.getOld_password()));
            if (flag) {
                userService.updatePassword(user_to_be_updated, dto.getNew_password());
            }
            return ApiResult.success("update password success");
        } else {
            return ApiResult.failed("no new info comes");
        }
    }

    @RequestMapping(value = "/getuserinfo", method = RequestMethod.GET)
    public ApiResult<User> getuserinfo(@RequestHeader(value = USER_NAME) String userName) {
        User user = userService.getUserByUsername(userName);
        return ApiResult.success(user);
    }

    @RequestMapping(value = "/getotherinfo", method = RequestMethod.GET)
    public ApiResult<User> getotherinfo(@RequestParam("user_id") String user_id) {
        User user = userService.getUserByUserid(user_id);
        return ApiResult.success(user);
    }

    @PostMapping("/upload")
    public ApiResult<String> singleFileUpload(@RequestParam("file") MultipartFile file, @RequestHeader(value = USER_NAME) String userName) {
//        SimpleDateFormat sdf =new SimpleDateFormat("/yyyy/MM/dd/");
//        String format =sdf.format(new Date());
//        String realPath =req.getServletContext().getRealPath("/")+format;
//        File folder =new File(realPath);
//        if(!folder.exists()){
//            folder.mkdirs();
//        }
        String newName = UUID.randomUUID().toString() + ".jpg";
        try {
            file.transferTo(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\",newName));
            User user = userService.getUserByUsername(userName);
            userService.uploadAvatar(user, newName);
        } catch (IOException e) {
            return ApiResult.failed(e.getMessage());
        }
        return ApiResult.success("upload success");
    }

    @RequestMapping("/getimgs")
    public void getimg(@RequestParam("userName") String userName, HttpServletResponse response) throws IOException {
        User user = userService.getUserByUsername(userName);
        if(!user.getAvatar().equals(null)){
            try {
                FileInputStream hFile = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\" + user.getAvatar());
                int i = hFile.available();
                byte data[] = new byte[i];
                hFile.read(data); //get file data
                hFile.close();
                response.setContentType("image/*");
                OutputStream toClient = response.getOutputStream();
                toClient.write(data);
                toClient.close();
            } catch (IOException e) {
                PrintWriter toClient = response.getWriter();
                response.setContentType("text/html;charset=gb2312");
                toClient.write("Can not open the picture");
                toClient.close();
            }
        }
    }

    @RequestMapping(value = "/getHistory", method = RequestMethod.GET)
    public ApiResult<Page<PostVO>> getHistory(@RequestHeader(value = USER_NAME) String userName,
                                              @RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize) {
        User user = userService.getUserByUsername(userName);
        Page<PostVO> results = postService.searchByUser(user.getId(), new Page<>(pageNum, pageSize));
        return ApiResult.success(results);
    }

    @RequestMapping(value = "/getLikedPost", method = RequestMethod.GET)
    public ApiResult<Page<PostVO>> getLikedPost(@RequestHeader(value = USER_NAME) String userName,
                                                @RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize) {
        User user = userService.getUserByUsername(userName);
        Page<PostVO> results = postService.searchByLiked(user.getId(), new Page<>(pageNum, pageSize));
        return ApiResult.success(results);
    }

    @RequestMapping(value = "/frozen", method = RequestMethod.POST)
    public ApiResult<String> frozen(@RequestHeader(value = USER_NAME) String userName, @RequestParam("user_id") String user_id) {
        if (userName.equals("admin")) {
            userService.freeze(user_id);
            return ApiResult.success("Freeze successfully");
        } else {
            return ApiResult.failed("You are not authorized to do that");
        }
    }

}


