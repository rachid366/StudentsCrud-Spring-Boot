package com.rachid.Crud.controller;

import com.rachid.Crud.model.Users;
import com.rachid.Crud.service.UserService;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

      @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(Users user){
        try {
            userService.register(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/login";
    }



}
