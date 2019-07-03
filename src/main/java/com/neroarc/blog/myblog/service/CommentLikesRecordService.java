package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.CommentLikesRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/4/26 9:01
 * Descripe:
 */
@Service
public interface CommentLikesRecordService {

    /**
     * 评论点赞
     * @param commentLikesRecord
     * @return
     */
    int commentLikeAction(CommentLikesRecord commentLikesRecord);

    /**
     * 得到当前用户文章评论点赞记录
     * @param articleId
     * @param userId
     * @return
     */
    List<String> getOwnCommentLikeId(long articleId,long userId);
}
