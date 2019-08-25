package com.neroarc.blog.myblog.repository;

import com.neroarc.blog.myblog.model.Image;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author: fjx
 * @date: 2019/8/23 17:21
 * Descripe:
 */
public interface ImageRepository extends ElasticsearchRepository<Image,Integer> {
}
