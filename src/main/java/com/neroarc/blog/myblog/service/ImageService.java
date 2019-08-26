package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.Image;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Size;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: fjx
 * @date: 2019/3/6 21:11
 * Descripe:
 */

@Service
public interface ImageService {

   /**
    * 上传图片
    * @param file 图片文件
    * @param subcatalog 子目录
    * @return
    */
   String uploadImage(File file,String subcatalog);

   /**
    * 添加新的背景图片
    * @param image
    * @return
    */
   int addBgImage(Image image);


   /**
    * 得到所有的背景图片
    * @return
    */
   List<Image> getBgAllImages();

   /**
    * 得到同一分类（不同用途）的图片
    * @param type
    * @return
    */
   List<Image> getBgImagesByType(int type);

   /**
    * 根据id获得图片
    * @param id
    * @return
    */
   Image getBgImageById(int id);

   /**
    * 根据标签获得背景图片
    * @param tag
    * @return
    */
   List<Image> getBgImageByTag(String tag);

   /**
    * 通过内容,通过es,搜索tag,description符合的内容
    * @param search
    * @return
    */
   List<Image> searchImageByEs(String search);

   /**
    * 分页得到图片
    * @param rows
    * @param pageNum
    * @return
    */
   Map<String,Object> getPageBgImage(int rows, int pageNum);

   /**
    * 更新背景图片
    * @param image
    * @return
    */
   int updateBgImage(Image image);
}
