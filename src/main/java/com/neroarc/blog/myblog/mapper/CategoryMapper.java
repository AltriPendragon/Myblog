package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.Article;
import com.neroarc.blog.myblog.model.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/3/6 15:20
 * Descripe:
 */

@Mapper
@Repository
public interface CategoryMapper {

    /**
     * 根据文章的分类获得相应的文章
     * @param category
     * @return
     */
    @Select("select article_id,author,left(create_time,10) create_time,title,category,tags from article where category = #{category} order by create_time desc")
    List<Article> getArticleByCategory(String category);

    /**
     * 获得分类的列表和相应文章的数目
     * @return
     */
    @Select("select category,count(*) category_num from article group by category")
    List<CategoryDTO> getCategoryDetail();
}
