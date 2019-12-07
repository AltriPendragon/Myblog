package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: fjx
 * @date: 2019/3/5 9:59
 * Descripe:
 */

@Mapper
@Repository
public interface TagsMapper {

    /**
     * 根据标签获得文章
     * @param tag
     * @return
     */
    @Select("select article_id,author,left(create_time,10) create_time,title,category,tags from article where tags like concat('%',#{tag},'%') order by create_time desc")
    List<Article> getArticlesByTag(String tag);

    /**
     * 获得标签云（旧）
     * @return
     */
    @Select("select tags from article")
    List<String> getTagsCloud();

    /**
     * 得到标签云（新）
     * @return
     */
    @Select("select count(*) num,e.tag from (select substring_index(substring_index(a.tags,',',b.record_id),',',-1) tag from article a join link_message_likes_record b on b.record_id<=((length(a.tags)-length(replace(a.tags,',','')))+1))e group by e.tag")
    List<Map<String,String>> getTagsCloudPlus();
}
