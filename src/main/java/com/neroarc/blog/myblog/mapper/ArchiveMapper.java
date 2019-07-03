package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.dto.ArchiveDTO;
import com.neroarc.blog.myblog.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/3/6 15:45
 * Descripe:
 */

@Mapper
@Repository
public interface ArchiveMapper {

    /**
     * 根据时间归档
     * @param time
     * @return
     */
    @Select("select article_id,author,left(create_time,10) create_time,title,category,tags from article where create_time like concat(#{time},'%') order by create_time desc")
    List<Article> getArchiveArticlesByTime(String time);

    /**
     * 获得详细的信息
     * @return
     */
    @Select("select t.*,count(*) num from (select left(create_time,7) create_time from article)t group by create_time desc")
    List<ArchiveDTO> getArchiveDetails();

    /**
     * 获得所有的归档文章
     * @return
     */
    @Select("select article_id,author,left(create_time,10) create_time,title,category,tags from article order by create_time desc")
    List<Article> getArchiveArticles();
}
