package com.lck.blog.web;

import com.lck.blog.dao.UserRepository;
import com.lck.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String loginPage(){
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(User user){
        userRepository.save(user);
        return "redirect:/login";
    }

}
