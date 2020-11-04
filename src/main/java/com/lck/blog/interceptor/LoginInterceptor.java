package com.lck.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//繼承springframework下的攔截處理器HandlerInterceptorAdapter
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //如果session內的user為空 讓他返回登入頁面 return false;不讓他往下執行
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return false;
        }
        //如果不等於空 return true 繼續往下執行
        return true;
    }
}
