package com.neroarc.blog.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neroarc.blog.myblog.mapper.CategoryMapper;
import com.neroarc.blog.myblog.model.Article;
import com.neroarc.blog.myblog.model.dto.CategoryDTO;
import com.neroarc.blog.myblog.service.CategoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/3/6 15:18
 * Descripe:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public JSONObject getArticlesByCategory(String category,int rows,int pageNum) {
        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum,rows);
        List<Article> articles = categoryMapper.getArticleByCategory(category);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        if(articles.size()!=0){
            returnJson.put("status",200);
            returnJson.put("result", JSONArray.fromObject(articles));
            returnJson.put("totalPage",pageInfo.getPages());
            returnJson.put("totalSize",pageInfo.getTotal());
            returnJson.put("num",pageInfo.getPageNum());

            return returnJson;
        }

        returnJson.put("status",404);
        return returnJson;
    }

    @Override
    public JSONObject getCategoryDetail() {
        JSONObject jsonObject = new JSONObject();
        List<CategoryDTO> categoryDTOS = categoryMapper.getCategoryDetail();
        if(categoryDTOS.size()>0){
            jsonObject.put("result",JSONArray.fromObject(categoryDTOS));
            jsonObject.put("state",200);

            return jsonObject;
        }

        jsonObject.put("state",500);
        return jsonObject;
    }
}
