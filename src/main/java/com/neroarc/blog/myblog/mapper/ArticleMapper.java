package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: fjx
 * @date: 2019/3/4 20:32
 * Descripe:
 */

@Mapper
@Repository
public interface ArticleMapper {

    /**
     * 得到文章
     * @param id
     * @return
     */
    @Select("select *from article where article_id = #{id}")
    Article getArticleById(long id);

    /**
     * 得到所有文章
     * @return
     */
    @Select("select article_id,author,left(create_time,10) create_time,title,summary,type,category,tags,`read`,comment from article order by create_time desc")
    List<Article> getArticles();

    /**
     * 添加文章
     * @param article
     * @return
     */
    @Insert("insert into article values(#{articleId},#{author},#{createTime},#{title},#{summary},#{content},#{type},#{category},#{tags},#{read},#{comment})")
    int addArticle(Article article);

    /**
     * 删除文章
     * @param id
     * @return
     */
    @Delete("delete from article where article_id = #{id}")
    int deleteArticle(long id);

    /**
     * 更新文章
     * @param article
     * @return
     */
    @Update("update article set author=#{author},title=#{title},summary=#{summary},content=#{content},type=#{type},category=#{category},tags=#{tags} where article_id=#{articleId}")
    int updateArticle(Article article);

    /**
     * 获得上一篇文章
     * @param id
     * @return
     */
    @Select("select article_id,title from article where article_id<#{id} order by article_id desc limit 1")
    Map<String,String> getLastArticle(long id);

    /**
     * 获得下一篇文章
     * @param id
     * @return
     */
    @Select("select article_id,title from article where article_id>#{id} limit 1")
    Map<String,String> getNextArticle(long id);

    /**
     * 更新阅读量
     * @param id
     * @return
     */
    @Update("update article set `read`=`read`+1 where article_id=#{id}")
    int updateArticleRead(long id);

    /**
     * 根据id获得文章的title
     * @param articleId
     * @return
     */
    @Select("select title from article where article_id=#{articleId}")
    String getArticleNameByArticleId(long articleId);
}
