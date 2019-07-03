package com.neroarc.blog.myblog.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: fjx
 * @date: 2019/5/9 19:06
 * Descripe:
 */

@Service
public interface AdminService {

    /**
     * 得到未阅读的评论
     * @param rows
     * @param pageNum
     * @return
     */
    JSONObject getNotReadingComment(int rows,int pageNum);

    /**
     * 得到未阅读的评论数目
     * @return
     */
    JSONObject getNotReadingCommentCount();

    /**
     * 更新评论阅读情况
     * @param commentId
     * @return
     */
    int updateCommentRead(long commentId);

    /**
     * 获得没有阅读的留言
     * @param rows
     * @param pageNum
     * @return
     */
    JSONObject getNotReadingLeaveMessage(int rows,int pageNum);

    /**
     * 获得未阅读的留言数目
     * @return
     */
    JSONObject getNotReadingLeaveMessageCount();

    /**
     * 更新留言阅读情况
     * @param messageId
     * @return
     */
    int updateMessageRead(long messageId);

    /**
     * 获得两类留言的未处理数目
     * @return
     */
    JSONObject getAllCounts();

    /**
     * 清空所有评论
     * @return
     */
    int clearAllComment();

    /**
     * 清空所有留言
     * @return
     */
    int clearAllMessage();

}
