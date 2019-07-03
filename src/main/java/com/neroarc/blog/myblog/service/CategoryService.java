package com.neroarc.blog.myblog.service;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: fjx
 * @date: 2019/3/6 15:16
 * Descripe:
 */
@Service
public interface CategoryService {

    /**
     * 根据分类得到文章
     * @param category
     * @param rows
     * @param pageNum
     * @return
     */
    JSONObject getArticlesByCategory(String category,int rows,int pageNum);

    /**
     * 获得文章的分类情况和各分类文章的数目
     * @return
     */
    JSONObject getCategoryDetail();
}
