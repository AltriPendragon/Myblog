package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.Image;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Size;
import java.io.File;
import java.util.List;

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
}
