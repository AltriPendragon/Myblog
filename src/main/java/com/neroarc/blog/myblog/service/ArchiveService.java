package com.neroarc.blog.myblog.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;


/**
 * @author: fjx
 * @date: 2019/3/6 15:42
 * Descripe:
 */

@Service
public interface ArchiveService {

    /**
     * 根据时间获得归档文章
     * @param time
     * @param rows
     * @param pageNum
     * @return
     */
    JSONObject getArchiveArticlesByTime(String time,int rows,int pageNum);

    /**
     * 获得归档的详细信息（时间，文章数）
     * @return
     */
    JSONObject getArchiveDetails();

    /**
     * 获得所有的归档文章并分页
     * @param rows
     * @param pageNum
     * @return
     */
    JSONObject getArchiveArticles(int rows,int pageNum);
}
