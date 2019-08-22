package com.neroarc.blog.myblog.control;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: fjx
 * @date: 2019/1/22 20:03
 * Descripe:
 */

@Controller
public class LoginControl {

    private String anonymousUser = "anonymousUser";

    @RequestMapping("/login")
    public String login(HttpServletRequest request){

        request.getSession().setAttribute("url",request.getHeader("Referer"));
        System.out.println(request.getHeader("Referer"));
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(!anonymousUser.equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return "fanIndex";
        }
        return "login";
    }

    @RequestMapping("/register")
    public String register(){
        if(!anonymousUser.equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return "fanIndex";
        }
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

    @RequestMapping("/daily")
    public String daily(){
        return "daily";
    }
}
