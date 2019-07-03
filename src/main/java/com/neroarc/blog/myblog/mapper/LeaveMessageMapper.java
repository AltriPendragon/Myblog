package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.LeaveMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/5 18:53
 * Descripe:
 */

@Mapper
@Repository
public interface LeaveMessageMapper {

    /**
     * 获得一级留言
     * @return
     */
    @Select("select message_id,remarker_id,responsor_id,p_id,message,likes,left(message_date,16) message_date from leave_message where p_id=0 order by message_date desc")
    List<LeaveMessage> getParentMessage();

    /**
     * 获得子留言
     * @param messageId
     * @return
     */
    @Select("select message_id,remarker_id,responsor_id,p_id,message,likes,left(message_date,16) message_date from leave_message where p_id=#{messageId} order by message_date desc")
    List<LeaveMessage> getChildrenMessage(long messageId);

    /**
     * 添加留言
     * @param leaveMessage
     * @return
     */
    @Insert("insert into leave_message(message_id,remarker_id,responsor_id,p_id,message,likes,message_date,is_read) values(#{messageId},#{remarkerId},#{responsorId},#{pId},#{message},#{likes},#{messageDate},#{isRead})")
    @Options(useGeneratedKeys = true,keyProperty = "messageId")
    int addLeaveMessage(LeaveMessage leaveMessage);

    /**
     * 点赞
     * @param messageId
     * @return
     */
    @Update("update leave_message set likes=likes+1 where message_id=#{messageId}")
    int increaseMessageLike(long messageId);

    /**
     * 取消点赞
     * @param messageId
     * @return
     */
    @Update("update leave_message set likes=likes-1 where message_id=#{messageId}")
    int decreaseMessageLike(long messageId);

    /**
     * 获得我的点赞记录
     * @param id
     * @return
     */
    @Select("select *from leave_message where remarker_id=#{id}")
    List<LeaveMessage> getLeaveMessageOfUser(long id);

}
