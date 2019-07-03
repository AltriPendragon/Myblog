package com.neroarc.blog.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neroarc.blog.myblog.mapper.ArticleMapper;
import com.neroarc.blog.myblog.model.Article;
import com.neroarc.blog.myblog.service.ArticleService;
import com.neroarc.blog.myblog.utils.DateUtil;
import com.neroarc.blog.myblog.utils.HtmlUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import java.util.List;
import java.util.Map;

/**
 * @author: fjx
 * @date: 2019/3/4 20:30
 * Descripe:
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final String AUTHOR = "Altri";

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public JSONObject getArticleById(long id) {
        Article article = articleMapper.getArticleById(id);
        JSONObject jsonObject = new JSONObject();

        if(article!=null){
            jsonObject = JSONObject.fromObject(article);
            jsonObject.put("status",200);

            return jsonObject;
        }

        jsonObject.put("status",404);
        return jsonObject;
    }

    @Override
    public JSONObject getPageArticles(int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        PageHelper.startPage(pageNum,rows);
        List<Article> articles = articleMapper.getArticles();

        if(articles.size()>0){
            PageInfo<Article> pageInfo = new PageInfo<>(articles);
            jsonArray = JSONArray.fromObject(articles);

            returnJson.put("totalPage",pageInfo.getPages());
            returnJson.put("totalSize",pageInfo.getTotal());
            returnJson.put("num",pageInfo.getPageNum());
            returnJson.put("status",200);
            returnJson.put("result",jsonArray);
            return returnJson;
        }

        returnJson.put("status",404);
        return returnJson;

    }


    @Override
    public int addArticle(Article article) {
        return articleMapper.addArticle(article);
    }

    @Override
    public int deleteArticle(long id) {
        return articleMapper.deleteArticle(id);
    }

    @Override
    public int updateArticle(Article article,String htmlStr) {
        String summary = getSummary(htmlStr);
        article.setSummary(summary);
        return articleMapper.updateArticle(article);
    }

    @Override
    public String getSummary(String htmlStr) {
        htmlStr = HtmlUtil.getTextFromHtml(htmlStr);
        String text = HtmlUtil.getSummary(htmlStr);
        if(text.length()<195){
            return text;
        }

        String summary = text.substring(0,195)+"...";
        return summary;
    }

    @Override
    public int publishArticle(Article article, String htmlStr) {

        String summary = getSummary(htmlStr);
        article.setSummary(summary);
        article.setCreateTime(DateUtil.getStringTime());
        if(article.getAuthor()==null){
            article.setAuthor(AUTHOR);
        }
        return articleMapper.addArticle(article);
    }


    @Override
    public JSONObject modifyArticleIdAndTitle(Map<String,String> raw) {
        JSONObject jsonObject = new JSONObject();
        if(raw!=null){
            jsonObject.put("articleId",raw.get("article_id"));
            jsonObject.put("title",raw.get("title"));
            jsonObject.put("flag",1);

        }

        else {
            jsonObject.put("flag",0);
        }
        return jsonObject;
    }


    @Override
    public JSONArray getLastAndNextArticles(int id) {
        Map<String,String> lastArticle = articleMapper.getLastArticle(id);
        Map<String,String> nextArticle = articleMapper.getNextArticle(id);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(modifyArticleIdAndTitle(lastArticle));
        jsonArray.add(modifyArticleIdAndTitle(nextArticle));
        return jsonArray;
    }


    @Override
    public int updateArticleRead(long id) {
        return articleMapper.updateArticleRead(id);
    }
}
