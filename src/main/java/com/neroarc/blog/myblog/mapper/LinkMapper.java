package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.Link;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/6/19 21:30
 * Descripe:
 */

@Repository
@Mapper
public interface LinkMapper {

    /**
     * 添加友链
     * @param link
     * @return
     */
    @Insert("insert into link(name,introduce,url,headImg,status) values(#{name},#{introduce},#{url},#{headImg},#{status})")
    int addLink(Link link);

    /**
     * 删除友链
     * @param id
     * @return
     */
    @Update("update link set status=1 where id=#{id}")
    int deleteLink(int id);

    /**
     * 更新友链
     * @param link
     * @return
     */
    @Update("update link set name=#{name},introduce=#{introduce},url=#{url},headImg=#{headImg} where id=#{id}")
    int updateLink(Link link);

    /**
     * 得到友链
     * @param id
     * @return
     */
    @Select("select *from link where id=#{id}")
    Link getLink(int id);

    /**
     * 得到所有未被删除的友链
     * @return
     */
    @Select("select *from link where status=0")
    List<Link> getLinks();
}
