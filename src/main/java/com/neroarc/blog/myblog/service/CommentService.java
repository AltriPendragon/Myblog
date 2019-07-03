package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.Comment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: fjx
 * @date: 2019/4/23 22:07
 * Descripe:
 */

@Service
public interface CommentService {

    /**
     * 添加文章评论
     * @param comment
     * @return
     */
    JSONObject addComment(Comment comment);


    /**
     * 获得文章的评论
     * @param articleId
     * @param userId
     * @return
     */
    JSONObject getCommentsOfArticle(long articleId,long userId);

}
