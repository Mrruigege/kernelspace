package com.rui.hyperlink.web.controller;

import com.rui.hyperlink.base.ApiResponse;
import com.rui.hyperlink.entity.User;
import com.rui.hyperlink.mapper.RoleMapper;
import com.rui.hyperlink.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaorui
 */
@Controller
@RequestMapping(value = "/api")
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "/user/login")
    public void login(@RequestParam("username") String name, @RequestParam("password") String password) {
        System.out.println(name);
        System.out.println(password);
    }

    @PostMapping(value = "/user/register")
    @Transactional
    @ResponseBody
    public ApiResponse register(User user) {
        String password = user.getPassword();
        String encodePass = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodePass);
        userMapper.insertUser(user);
        roleMapper.insertRoleByUserId(user.getId());
        return ApiResponse.ofSuccess("null");
    }

    @GetMapping(value = "/user/test")
    @ResponseBody
    public String get() {
        return "hello";
    }
}
