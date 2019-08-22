package com.neroarc.blog.myblog.mapper;

import com.neroarc.blog.myblog.model.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/8/9 13:29
 * Descripe:
 */

@Mapper
@Repository
public interface ImageMapper {

    /**
     * 添加新的背景图片
     * @param image
     * @return
     */
    @Insert("insert into image(url,type,tag,description) values(#{url},#{type},#{tag},#{description})")
    int addBgImage(Image image);

    /**
     * 得到所有的背景图片
     * @return
     */
    @Select("select *from image")
    List<Image> getBgAllImages();

    /**
     * 得到同一分类（不同用途）的图片
     * @param type
     * @return
     */
    @Select("select *from image where type=#{type}")
    List<Image> getBgImagesByType(int type);

    /**
     * 根据id获得图片
     * @param id
     * @return
     */
    @Select("select *from image where id=#{id}")
    Image getBgImageById(int id);


    /**
     * 根据标签获得背景图片
     * @param tag
     * @return
     */
    @Select("select *from image where tag like concat('%',#{tag},'%')")
    List<Image> getBgImageByTag(String tag);
}
