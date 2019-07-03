package com.neroarc.blog.myblog.service.impl;

import com.neroarc.blog.myblog.service.ImageService;
import com.neroarc.blog.myblog.utils.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author: fjx
 * @date: 2019/3/6 21:16
 * Descripe:
 */

@Service
public class ImageServiceImpl implements ImageService {

    FileUtil fileUtil = new FileUtil();

    @Override
    public String uploadImage(File file, String subcatalog) {
        String url = fileUtil.uploadFile(file,subcatalog);
        return url;
    }
}
