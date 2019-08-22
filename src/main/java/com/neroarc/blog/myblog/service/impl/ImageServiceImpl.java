package com.neroarc.blog.myblog.service.impl;

import com.neroarc.blog.myblog.mapper.ImageMapper;
import com.neroarc.blog.myblog.model.Image;
import com.neroarc.blog.myblog.service.ImageService;
import com.neroarc.blog.myblog.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author: fjx
 * @date: 2019/3/6 21:16
 * Descripe:
 */

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    private FileUtil fileUtil = new FileUtil();

    @Override
    public String uploadImage(File file, String subcatalog) {
        String url = fileUtil.uploadFile(file,subcatalog);
        return url;
    }

    @Override
    public int addBgImage(Image image) {
        return imageMapper.addBgImage(image);
    }

    @Override
    public List<Image> getBgAllImages() {
        return imageMapper.getBgAllImages();
    }

    @Override
    public List<Image> getBgImagesByType(int type) {
        return imageMapper.getBgImagesByType(type);
    }

    @Override
    public Image getBgImageById(int id) {
        return imageMapper.getBgImageById(id);
    }

    @Override
    public List<Image> getBgImageByTag(String tag) {
        return imageMapper.getBgImageByTag(tag);
    }
}
