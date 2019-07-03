package com.neroarc.blog.myblog.control;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: fjx
 * @date: 2019/1/22 20:03
 * Descripe:
 */

@Controller
public class LoginControl {

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        request.getSession().setAttribute("url",request.getHeader("Referer"));
        System.out.println(request.getHeader("Referer"));
        return "login";
    }

    @RequestMapping("/register")
    public String register(){
        return "login";
    }

    @RequestMapping("/success")
    @PreAuthorize("hasAnyRole('USER')")
    public String success(){
        return "success";
    }

    @RequestMapping("/")
    public String index(){
        return "fanIndex";
    }

    @RequestMapping("/signin")
    public String signin(){
        return "/login1";
    }

    @RequestMapping("/show")
    public String test(){
        return "passageTest";
    }
}
