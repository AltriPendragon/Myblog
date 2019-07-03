package com.neroarc.blog.myblog.control;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author: fjx
 * @date: 2019/4/14 18:02
 * Descripe:
 */

@Controller
public class BackgroundControl {


    @RequestMapping("/article/{id}")
    public String readArticle(){
//        response.setHeader("articleId",Long.toString(id));
        //request.getSession().setAttribute("articleId",id);
        return "article";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/edit")
    public String edit(){
        return "edit";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/edit/{articleId}")
    public String editArticle(){
        return "edit";
    }

    @RequestMapping("/archive/{date}")
    public String archiveDate(){
        return "archive";
    }

    @RequestMapping("/archive")
    public String archive(){
        return "archive";
    }

    @RequestMapping("/category")
    public String category(){
        return "category";
    }

    @RequestMapping("/tag")
    public String tag(){
        return "tag";
    }

    @RequestMapping("/leaveMessage")
    public String leaveMessage(@AuthenticationPrincipal Principal principal){
        return "leaveMessage";
    }

    @RequestMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String backGround(){
        return "user";
    }

    @RequestMapping("/aboutme")
    public String aboutMe(){
        return "about";
    }

    @RequestMapping("/link")
    public String link(){
        return "links";
    }

    @RequestMapping("/leaveMessage2")
    public String leaveMessage2(){
        return "leaveMessage2";
    }

    @RequestMapping("/link2")
    public String link2(){
        return "link2";
    }




}
