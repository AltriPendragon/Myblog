package com.neroarc.blog.myblog.mapper;

import net.sf.json.JSONArray;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: fjx
 * @date: 2019/11/26 19:42
 * Descripe:
 */
@Mapper
@Repository
public interface MusicMapper {

    /**
     * 将歌曲数据存储在数据库中（未采用）
     * @param musics
     * @return
     */
    @Insert("<script>insert into music(name,author,src,cover,lrc)" +
            "   values " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.name},#{item.artist},#{item.url},#{item.cover},#{item.lrc})" +
            "</foreach></script>")
    int pullAllMusic(JSONArray musics);

    /**
     * 获得歌单中所有歌曲的详细信息(别名为song_id适配前端)
     * @return
     */
    @Select("select id as song_id,name,author,src,cover,lrc from music")
    List<Map<String,Object>> getMusics();


    /**
     * 根据歌曲的id获得相应的歌曲url
     * @param id
     * @return
     */
    @Select("select src as url from music where id=#{id}")
    Map<String,Object> getMusicUrl(String id);

}
