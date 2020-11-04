package com.lck.blog.web;

import com.lck.blog.po.User;
import com.lck.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "login";
    }

    @PostMapping
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        //如果user不為空 代表帳號密碼正確
        if(user != null){
            //不把密碼傳到前面去(為了安全性)
            user.setPassword(null);
            //把user放進session
            session.setAttribute("user",user);
            //與RedirectAttributes attributes對應
            attributes.addFlashAttribute("message1","成功登入!5秒後自動返回首頁");
            System.out.println(attributes.getFlashAttributes());
            return "ok";
        }else {
            ////與RedirectAttributes attributes對應
            attributes.addFlashAttribute("message","帳號或密碼錯誤");
            //如果直接返回登入頁面會有問題所以使用redirect:/login
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //清除session
        session.removeAttribute("user");
        return "logout";

    }
}
