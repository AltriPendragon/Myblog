package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.LeaveMessageLikesRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/5 19:16
 * Descripe:
 */
@Mapper
@Repository
public interface LeaveMessageLikesRecordMapper {

    /**
     * 获得用户留言点赞的记录id
     * @param userId
     * @return
     */
    @Select("select message_id from leave_message_likes_record where liker_id=#{userId} and flag!=0")
    List<String> getOwnLeaveMessageLikeId(@Param("userId") long userId);

    /**
     * 获得留言点赞记录的状态
     * @param recordId
     * @return
     */
    @Select("select flag from leave_message_likes_record where record_id=#{recordId}")
    int getFlagByRecordId(long recordId);

    /**
     * 根据linkId与likerId得到用户对某条记录的点赞状态
     * @param leaveMessageLikesRecord
     * @return
     */
    @Select("select flag from leave_message_likes_record where message_id=#{messageId} and liker_id=#{likerId}")
    int getFlagByOthers(LeaveMessageLikesRecord leaveMessageLikesRecord);

    /**
     * 得到用户我点赞的记录
     * @param userId
     * @return
     */
    @Select("select *from leave_message_likes_record where liker_id=#{userId} and flag=1")
    List<LeaveMessageLikesRecord> getOwnLikeRecords(long userId);


    /**
     * 判断点赞记录是否存在
     * @param leaveMessageLikesRecord
     * @return
     */
    @Select("select exists(select 1 from leave_message_likes_record where message_id=#{messageId} and liker_id=#{likerId})")
    int isMessageRecordExist(LeaveMessageLikesRecord leaveMessageLikesRecord);

    /**
     * 点赞
     * @param leaveMessageLikesRecord
     * @return
     */
    @Update("update leave_message_likes_record set flag=1,date=#{date} where message_id=#{messageId} and liker_id=#{likerId}")
    int likeMessage(LeaveMessageLikesRecord leaveMessageLikesRecord);

    /**
     * 取消点赞
     * @param leaveMessageLikesRecord
     * @return
     */
    @Update("update leave_message_likes_record set flag=0,date=#{date} where message_id=#{messageId} and liker_id=#{likerId}")
    int cancelLikeMessage(LeaveMessageLikesRecord leaveMessageLikesRecord);

    /**
     * 添加点赞记录
     * @param leaveMessageLikesRecord
     * @return
     */
    @Insert("insert into leave_message_likes_record(record_id,message_id,liker_id,flag,date) values(#{recordId},#{messageId},#{likerId},1,#{date})")
    int addLikeMessage(LeaveMessageLikesRecord leaveMessageLikesRecord);
}
