package com.neroarc.blog.myblog.service;

import org.springframework.stereotype.Service;

import java.io.File;

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
}
