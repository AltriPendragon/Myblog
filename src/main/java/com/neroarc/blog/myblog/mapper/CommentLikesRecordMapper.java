package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.CommentLikesRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/4/26 9:18
 * Descripe:
 */

@Mapper
@Repository
public interface CommentLikesRecordMapper {

    /**
     * 得到评论点赞记录的状态
     * @param recordId
     * @return
     */
    @Select("select flag from comment_likes_record where record_id=#{recordId}")
    int getFlagByRecordId(long recordId);

    /**
     * 得到评论点赞记录的状态
     * @param commentLikesRecord
     * @return
     */
    @Select("select flag from comment_likes_record where comment_id=#{commentId} and liker_id=#{likerId}")
    int getFlagByOthers(CommentLikesRecord commentLikesRecord);

    /**
     * 得到用户自己的点赞记录
     * @param userId
     * @return
     */
    @Select("select *from comment_likes_record where liker_id=#{userId} and flag=1")
    List<CommentLikesRecord> getOwnLikeRecords(long userId);

    /**
     * 得到用户点赞记录的id
     * @param articleId
     * @param userId
     * @return
     */
    @Select("select comment_id from comment_likes_record where article_id=#{articleId} and liker_id=#{userId} and flag!=0")
    List<String> getOwnCommentLikeId(@Param("articleId") long articleId, @Param("userId") long userId);

    /**
     * 判断记录是否存在
     * @param commentLikesRecord
     * @return
     */
    @Select("select exists(select 1 from comment_likes_record where comment_id=#{commentId} and liker_id=#{likerId})")
    int isCommentRecordExist(CommentLikesRecord commentLikesRecord);

    /**
     * 点赞
     * @param commentLikesRecord
     * @return
     */
    @Update("update comment_likes_record set flag=1,date=#{date} where comment_id=#{commentId} and liker_id=#{likerId}")
    int likeComment(CommentLikesRecord commentLikesRecord);

    /**
     * 取消点赞
     * @param commentLikesRecord
     * @return
     */
    @Update("update comment_likes_record set flag=0,date=#{date} where comment_id=#{commentId} and liker_id=#{likerId}")
    int cancelLikeComment(CommentLikesRecord commentLikesRecord);

    /**
     * 添加点赞记录
     * @param commentLikesRecord
     * @return
     */
    @Insert("insert into comment_likes_record(article_id,comment_id,liker_id,flag,date) values(#{articleId},#{commentId},#{likerId},1,#{date})")
    int addLikeComment(CommentLikesRecord commentLikesRecord);

}
