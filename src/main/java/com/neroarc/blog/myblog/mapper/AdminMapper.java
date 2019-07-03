package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.Comment;
import com.neroarc.blog.myblog.model.LeaveMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/9 19:16
 * Descripe:
 */

@Mapper
@Repository
public interface AdminMapper {

    /**
     * 获得没有处理的评论
     * @return
     */
    @Select("select comment_id,remarker_id,article_id,comment_content,left(comment_date,10) comment_date,is_read from comment where is_read=0 order by comment_date desc")
    List<Comment> getNotReadingComment();

    /**
     * 获得没有处理评论的数目
     * @return
     */
    @Select("select count(*) count from comment where is_read=0")
    int getNotReadingCommentCount();

    /**
     * 更新阅读状态
     * @param commentId
     * @return
     */
    @Update("update comment set is_read=1 where comment_id=#{commentId}")
    int updateCommentRead(long commentId);

    /**
     * 获得没有处理的留言
     * @return
     */
    @Select("select message_id,remarker_id,message,left(message_date,10) message_date,is_read from leave_message where is_read=0 order by message_date desc")
    List<LeaveMessage> getNotReadingLeaveMessage();

    /**
     * 获得没有处理留言的数目
     * @return
     */
    @Select("select count(*) count from leave_message where is_read=0")
    int getNotReadingLeaveMessageCount();

    /**
     * 更新阅读状态
     * @param messageId
     * @return
     */
    @Update("update leave_message set is_read=1 where message_id=#{commentId}")
    int updateMessageRead(long messageId);

    /**
     * 清空
     * @return
     */
    @Update("update comment set is_read=1")
    int clearAllComment();

    /**
     * 清空
     * @return
     */
    @Update("update leave_message set is_read=1")
    int clearAllMessage();

}
