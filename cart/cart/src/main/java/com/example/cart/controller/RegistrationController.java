package com.example.cart.controller;


import com.example.cart.model.User;
import com.example.cart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping(value = "/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.user",
                    "已经有一个用户注册了所提供的电子邮件");
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.user",
                    "已经有一个使用提供的用户名注册的用户");
        }
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            //注册失败，返回错误信息
            modelAndView.setViewName("/registration");
        } else {
            // 注册成功
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "用户已成功注册");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/registration");
        }
        return modelAndView;
    }
}
