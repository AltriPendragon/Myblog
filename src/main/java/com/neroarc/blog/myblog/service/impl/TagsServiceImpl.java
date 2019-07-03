package com.neroarc.blog.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neroarc.blog.myblog.mapper.TagsMapper;
import com.neroarc.blog.myblog.model.Article;
import com.neroarc.blog.myblog.service.TagsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: fjx
 * @date: 2019/3/5 9:58
 * Descripe:
 */

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;



    /**
     * 根据标签获得文章
     * @param tag 标签名称
     * @param rows 一页数据条数
     * @param pageNum 第几页
     * @return
     */
    @Override
    public JSONObject getArticlesByTag(String tag, int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum,rows);
        List<Article> articles = tagsMapper.getArticlesByTag(tag);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

       if(articles.size()!=0){
           returnJson.put("result",JSONArray.fromObject(articles));
           returnJson.put("status",200);
           returnJson.put("totalPage",pageInfo.getPages());
           returnJson.put("totalSize",pageInfo.getTotal());
           returnJson.put("num",pageInfo.getPageNum());
           return returnJson;
       }

        returnJson.put("status",404);
        return returnJson;
    }


    /**
     * 获得所有的标签
     * @return
     */
    @Override
    public JSONObject getTagsCloud() {
        List<String> rawTags = tagsMapper.getTagsCloud();
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Set<String> tags = new HashSet<>();
        for(String str:rawTags){
            String strArray[] = str.split(",");
            for(int i=0;i<strArray.length;i++){
                tags.add(strArray[i]);
            }
        }

        if(!tags.isEmpty()){
            for(String tag:tags){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tag",tag);

                //10-17
                jsonObject.put("tagSize",(int)((Math.random()*7))+10);
                jsonArray.add(jsonObject);
            }

            returnJson.put("result",jsonArray);
            returnJson.put("status",200);
            return returnJson;
        }

        returnJson.put("status",404);
        return returnJson;
    }

    /**
     * 在数据库进行分类计数，获得所有标签
     * @return
     */
    @Override
    public JSONObject getTagsCloudPlus() {
        int count = 0;
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<Map<String,String>> list = tagsMapper.getTagsCloudPlus();
        if(list.size()>0){
            for(Map<String,String> map:list){

                //注意这里的值转换
                String num  = String.valueOf(map.get("num"));
                String tag = map.get("tag");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tag",tag);
                jsonObject.put("tagSize",Integer.valueOf(num)+10);

                jsonArray.add(jsonObject);
                count++;
            }

            returnJson.put("result",jsonArray);
            returnJson.put("count",count);
            returnJson.put("status",200);

            return returnJson;
        }

        returnJson.put("status",500);
        return returnJson;
    }
}
