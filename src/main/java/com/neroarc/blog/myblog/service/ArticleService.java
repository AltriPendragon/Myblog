package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.Article;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author: fjx
 * @date: 2019/3/4 20:28
 * Descripe:
 */

@Service
public interface ArticleService {

    /**
     * 根据文章的id获得文章
     * @param id
     * @return
     */
    JSONObject getArticleById(long id);

    /**
     * 分页获得相应的文章
     * @param rows
     * @param pageNum
     * @return
     */
    JSONObject getPageArticles(int rows, int pageNum);

    /**
     * 添加文章
     * @param article
     * @return
     */
    int addArticle(Article article);

    /**
     * 根据文章id删除文章
     * @param id
     * @return
     */
    int deleteArticle(long id);

    /**
     * 更新文章
     * @param article
     * @param htmlStr
     * @return
     */
    int updateArticle(Article article,String htmlStr);

    /**
     * 得到文章的摘要
     * @param htmlStr
     * @return
     */
    String getSummary(String htmlStr);

    /**
     * 发布文章
     * @param article
     * @param htmlStr
     * @return
     */
    int publishArticle(Article article,String htmlStr);

    /**
     * 得到文章的上一篇和下一篇
     * @param id
     * @return
     */
    JSONArray getLastAndNextArticles(int id);

    /**
     *根据获得的上下两篇文章的信息，设置文章的id和标题
     * @param raw
     * @return
     */
    JSONObject modifyArticleIdAndTitle(Map<String,String> raw);

    /**
     * 更新文章的阅读数
     * @param id
     * @return
     */
    int updateArticleRead(long id);
}
