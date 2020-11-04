package com.lck.blog.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TextController {


    @GetMapping("/article")
    public String text(){
        return "article" ;
    }

    @GetMapping("/home")
    public String text2(){
        return "home";
    }
}
