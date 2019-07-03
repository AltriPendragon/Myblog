package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.Link;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/6/19 21:27
 * Descripe:
 */

@Service
public interface LinkService {

    /**
     * 添加友链
     * @param link
     * @return
     */
    int addLink(Link link);

    /**
     * 删除友链
     * @param id
     * @return
     */
    int deleteLink(int id);

    /**
     * 更新友链
     * @param link
     * @return
     */
    int updateLink(Link link);

    /**
     * 根据友链id获得友链的全部信息
     * @param id
     * @return
     */
    JSONObject getLink(int id);

    /**
     * 根据行与页数获得友链
     * @param rows
     * @param pageNum
     * @return
     */
    JSONObject getLinks(int rows,int pageNum);

    /**
     * 获得所有的友链
     * @return
     */
    JSONObject getAllLinks();
}
