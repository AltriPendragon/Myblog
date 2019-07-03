package com.neroarc.blog.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neroarc.blog.myblog.mapper.LinkMapper;
import com.neroarc.blog.myblog.model.Link;
import com.neroarc.blog.myblog.service.LinkService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/6/19 21:29
 * Descripe:
 */

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public int addLink(Link link) {

        return linkMapper.addLink(link);
    }

    @Override
    public int deleteLink(int id) {
        return linkMapper.deleteLink(id);
    }

    @Override
    public int updateLink(Link link) {
        return linkMapper.updateLink(link);
    }

    @Override
    public JSONObject getLink(int id) {
        JSONObject jsonObject = new JSONObject();
        Link link = linkMapper.getLink(id);
        jsonObject.put("status",500);

        if(link!=null){
            jsonObject = JSONObject.fromObject(link);
            jsonObject.put("status",200);
        }

        return jsonObject;
    }

    @Override
    public JSONObject getLinks(int rows,int pageNum) {
        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum,rows);

        List<Link> links = linkMapper.getLinks();
        PageInfo<Link> pageInfo = new PageInfo<>(links);


        if(links.size()>0){
            returnJson.put("result",JSONArray.fromObject(links));

            returnJson.put("totalPage",pageInfo.getPages());
            returnJson.put("totalSize",pageInfo.getTotal());
            returnJson.put("num",pageInfo.getPageNum());
            returnJson.put("status",200);

            return returnJson;
        }

        returnJson.put("status",500);
        return returnJson;
    }

    @Override
    public JSONObject getAllLinks() {
        JSONObject returnJson = new JSONObject();
        List<Link> links = linkMapper.getLinks();

        if(links.size()>0){
            returnJson.put("result",JSONArray.fromObject(links));
            returnJson.put("status",200);

            return returnJson;
        }

        returnJson.put("status",500);
        return returnJson;
    }
}
