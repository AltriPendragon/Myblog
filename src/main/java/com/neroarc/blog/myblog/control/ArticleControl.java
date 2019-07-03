package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.model.Article;
import com.neroarc.blog.myblog.model.User;
import com.neroarc.blog.myblog.service.ArticleService;
import com.neroarc.blog.myblog.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author: fjx
 * @date: 2019/3/4 20:47
 * Descripe:
 */

@RestController
public class ArticleControl {

    @Autowired
    private ArticleService articleService;


    @RequestMapping("/getArticleById")
    public JSONObject getArticleById(long id, HttpServletRequest request){
        if(request.getSession().getAttribute(Long.toString(id))==null){
            articleService.updateArticleRead(id);
            request.getSession().setAttribute(Long.toString(id),id);
        }

        return articleService.getArticleById(id);
    }

    @PostMapping("/getPageArticles")
    public JSONObject getArticles(int rows, int pageNum){
        return articleService.getPageArticles(rows,pageNum);
    }

    @RequestMapping("/addArticle")
    public JSONObject addArticle(Article article){
        JSONObject jsonObject = new JSONObject();
        int flag = articleService.addArticle(article);
        if(flag!=0){
            jsonObject.put("status",200);
            return jsonObject;
        }

        jsonObject.put("status",404);
        return jsonObject;
    }

    @RequestMapping("/deleteArticle")
    public JSONObject deleteArticle(long id){
        JSONObject jsonObject = new JSONObject();
        int flag = articleService.deleteArticle(id);
        if(flag!=0){
            jsonObject.put("status",200);
            return jsonObject;
        }

        jsonObject.put("status",404);
        return jsonObject;
    }

    @PostMapping("/updateArticle")
    public JSONObject updateArticle(@RequestBody Article article){

        JSONObject jsonObject = new JSONObject();
        int flag = articleService.updateArticle(article,article.getHtmlStr());
        if(flag!=0){
            jsonObject.put("status",200);
            return jsonObject;
        }

        jsonObject.put("status",404);
        return jsonObject;
    }


    @RequestMapping("/getSummary")
    public JSONObject getSummary(String htmlStr){
        String str = articleService.getSummary(htmlStr);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",str);

        return jsonObject;
    }

    @PostMapping("/publishArticle")
    public JSONObject publishArticle(@RequestBody Article article,@AuthenticationPrincipal Principal principal){
        JSONObject jsonObject = new JSONObject();
        int flag = articleService.publishArticle(article,article.getHtmlStr());

        if(flag!=0){
            jsonObject.put("summary",articleService.getSummary(article.getHtmlStr()));
            jsonObject.put("title",article.getTitle());
            jsonObject.put("status",200);
            return jsonObject;
        }

        jsonObject.put("status",404);
        return jsonObject;
    }



    @RequestMapping("/getLastAndNextArticles")
    public JSONArray getLastAndNextArticles(int articleId){
        return articleService.getLastAndNextArticles(articleId);
    }
}
