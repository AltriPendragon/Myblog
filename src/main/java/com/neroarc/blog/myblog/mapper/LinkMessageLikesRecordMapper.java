package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.LinkMessageLikesRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/25 19:55
 * Descripe:
 */

@Mapper
@Repository
public interface LinkMessageLikesRecordMapper {

    /**
     * 获得用户点赞的友链留言
     * @param userId
     * @return
     */
    @Select("select link_id from link_message_likes_record where liker_id=#{userId} and flag!=0")
    List<String> getOwnLeaveMessageLikeId(@Param("userId") long userId);

    /**
     * 获得特定喜欢记录的状态（点赞or取消点赞）
     * @param recordId
     * @return
     */
    @Select("select flag from link_message_likes_record where record_id=#{recordId}")
    int getFlagByRecordId(long recordId);

    /**
     * 根据linkId与likerId得到用户对某条记录的点赞状态
     * @param linkMessageLikesRecord
     * @return
     */
    @Select("select flag from link_message_likes_record where link_id=#{linkId} and liker_id=#{likerId}")
    int getFlagByOthers(LinkMessageLikesRecord linkMessageLikesRecord);

    /**
     * 获得用户点赞过的记录的信息
     * @param userId
     * @return
     */
    @Select("select *from link_message_likes_record where liker_id=#{userId} and flag=1")
    List<LinkMessageLikesRecord> getOwnLikeRecords(long userId);


    /**
     * 查看友链点赞记录是否存在
     * @param linkMessageLikesRecord
     * @return
     */
    @Select("select exists(select 1 from link_message_likes_record where link_id=#{linkId} and liker_id=#{likerId})")
    int isLinkRecordExist(LinkMessageLikesRecord linkMessageLikesRecord);

    /**
     * 点赞
     * @param linkMessageLikesRecord
     * @return
     */
    @Update("update link_message_likes_record set flag=1,date=#{date} where link_id=#{linkId} and liker_id=#{likerId}")
    int likeLinkMessage(LinkMessageLikesRecord linkMessageLikesRecord);

    /**
     * 取消点赞
     * @param linkMessageLikesRecord
     * @return
     */
    @Update("update link_message_likes_record set flag=0,date=#{date} where link_id=#{linkId} and liker_id=#{likerId}")
    int cancelLikeLinkMessage(LinkMessageLikesRecord linkMessageLikesRecord);

    /**
     * 添加友链留言喜欢记录
     * @param linkMessageLikesRecord
     * @return
     */
    @Insert("insert into link_message_likes_record(record_id,link_id,liker_id,flag,date) values(#{recordId},#{linkId},#{likerId},1,#{date})")
    int addLikeLinkMessage(LinkMessageLikesRecord linkMessageLikesRecord);
}
