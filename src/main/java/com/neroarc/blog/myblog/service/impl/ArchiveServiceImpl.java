package com.neroarc.blog.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neroarc.blog.myblog.mapper.ArchiveMapper;
import com.neroarc.blog.myblog.model.dto.ArchiveDTO;
import com.neroarc.blog.myblog.model.Article;
import com.neroarc.blog.myblog.service.ArchiveService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/3/6 15:44
 * Descripe:
 */
@Service
public class ArchiveServiceImpl implements ArchiveService {


    @Autowired
    private ArchiveMapper archiveMapper;

    @Override
    public JSONObject getArchiveArticlesByTime(String time,int rows,int pageNum) {

        String parameter = time.substring(0,7);

        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum,rows);
        List<Article> articles = archiveMapper.getArchiveArticlesByTime(parameter);


        if(articles.size()!=0){
            PageInfo<Article> pageInfo = new PageInfo<>(articles);
            returnJson.put("status",200);
            returnJson.put("result",JSONArray.fromObject(articles));
            returnJson.put("totalSize",pageInfo.getTotal());
            returnJson.put("totalPage",pageInfo.getPages());
            returnJson.put("num",pageInfo.getPageNum());

            return returnJson;
        }

        returnJson.put("status",404);
        return returnJson;
    }



    @Override
    public JSONObject getArchiveDetails() {
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<ArchiveDTO> createTimes = archiveMapper.getArchiveDetails();
        if(createTimes.size()>0){
            for(ArchiveDTO archiveDTO:createTimes){
                JSONObject jsonObject = new JSONObject();
                String parameter[] = archiveDTO.getCreateTime().split("-");
                jsonObject.put("archiveName",parameter[0]+"年"+parameter[1]+"月");
                jsonObject.put("archiveNum",archiveDTO.getNum());
                jsonArray.add(jsonObject);
            }
            returnJson.put("data",jsonArray);
            returnJson.put("status",200);
            return returnJson;
        }


        returnJson.put("status",500);
        return returnJson;
    }

    @Override
    public JSONObject getArchiveArticles(int rows,int pageNum) {

        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum,rows);
        List<Article> articles = archiveMapper.getArchiveArticles();

        if(articles.size()>0){
            PageInfo<Article> pageInfo = new PageInfo<>(articles);
            returnJson.put("result",JSONArray.fromObject(articles));
            returnJson.put("num",pageInfo.getPageNum());
            returnJson.put("totalSize",pageInfo.getTotal());
            returnJson.put("totalPage",pageInfo.getPages());
            returnJson.put("status",200);

            return returnJson;

        }

        returnJson.put("status",404);

        return returnJson;
    }
}
