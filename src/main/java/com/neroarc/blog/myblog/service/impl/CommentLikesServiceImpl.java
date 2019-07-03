package com.neroarc.blog.myblog.service.impl;

import com.neroarc.blog.myblog.mapper.CommentLikesRecordMapper;
import com.neroarc.blog.myblog.mapper.CommentMapper;
import com.neroarc.blog.myblog.model.CommentLikesRecord;
import com.neroarc.blog.myblog.service.CommentLikesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/4/26 9:15
 * Descripe:
 */

@Service
public class CommentLikesServiceImpl implements CommentLikesRecordService {

    @Autowired
    private CommentLikesRecordMapper commentLikesRecordMapper;

    @Autowired
    private CommentMapper commentMapper;


    /**
     *
     * @param commentLikesRecord
     * @return
     * 返回当前flag状态，点击后
     */
    @Override
    public int commentLikeAction(CommentLikesRecord commentLikesRecord) {
        int flag = commentLikesRecordMapper.isCommentRecordExist(commentLikesRecord);
        if(flag==1){
            if(commentLikesRecordMapper.getFlagByOthers(commentLikesRecord)==1){
                commentLikesRecordMapper.cancelLikeComment(commentLikesRecord);
                commentMapper.decreaseCommentLike(commentLikesRecord.getCommentId());
                return 0;
            }

            else {
                commentLikesRecordMapper.likeComment(commentLikesRecord);
                commentMapper.increaseCommentLike(commentLikesRecord.getCommentId());
                return 1;
            }
        }

        commentLikesRecordMapper.addLikeComment(commentLikesRecord);
        commentMapper.increaseCommentLike(commentLikesRecord.getCommentId());
        return 1;
    }


    @Override
    public List<String> getOwnCommentLikeId(long articleId, long userId) {
        List<String> commentIds = commentLikesRecordMapper.getOwnCommentLikeId(articleId,userId);
        return commentIds;
    }
}
