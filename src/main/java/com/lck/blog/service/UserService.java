package com.lck.blog.service;

import com.lck.blog.po.User;


public interface UserService {

    User checkUser(String username, String password); //檢查帳號密碼
}
