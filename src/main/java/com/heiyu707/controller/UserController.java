package com.heiyu707.controller;


import com.heiyu707.entity.User;
import com.heiyu707.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录方法
     */
    @PostMapping("/login")
    public String login(User user, HttpSession session) {
        User userDB = userService.login(user);
        if (userDB != null) {
            session.setAttribute("user", userDB);
            return "redirect:/file/showAll";
        } else {
            return "redirect:/index";
        }
    }
}
