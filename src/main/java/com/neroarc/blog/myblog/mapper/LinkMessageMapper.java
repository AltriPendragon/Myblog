package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.LinkMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/25 19:50
 * Descripe:
 */
@Mapper
@Repository
public interface LinkMessageMapper {

    /**
     * 获得一级友链留言
     * @return
     */
    @Select("select link_id,remarker_id,responsor_id,p_id,message,likes,left(message_date,16) message_date from link_message where p_id=0 order by message_date desc")
    List<LinkMessage> getParentMessage();

    /**
     * 根据一级留言的编号获得其下的子评论
     * @param linkId
     * @return
     */
    @Select("select link_id,remarker_id,responsor_id,p_id,message,likes,left(message_date,16) message_date from link_message where p_id=#{linkId} order by message_date desc")
    List<LinkMessage> getChildrenMessage(long linkId);

    /**
     * 发表友链留言
     * @param linkMessage
     * @return
     */
    @Insert("insert into link_message(link_id,remarker_id,responsor_id,p_id,message,likes,message_date,is_read) values(#{linkId},#{remarkerId},#{responsorId},#{pId},#{message},#{likes},#{messageDate},#{isRead})")
    @Options(useGeneratedKeys = true,keyProperty = "linkId")
    int addLinkMessage(LinkMessage linkMessage);

    /**
     * 点赞加一
     * @param linkId
     * @return
     */
    @Update("update link_message set likes=likes+1 where link_id=#{linkId}")
    int increaseMessageLike(long linkId);

    /**
     * 取消减一
     * @param linkId
     * @return
     */
    @Update("update link_message set likes=likes-1 where link_id=#{linkId}")
    int decreaseMessageLike(long linkId);

    /**
     * 获得我在友链下的留言
     * @param id
     * @return
     */
    @Select("select *from link_message where remarker_id=#{id}")
    List<LinkMessage> getLinkOfUser(long id);
}
