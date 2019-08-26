package com.neroarc.blog.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neroarc.blog.myblog.mapper.ImageMapper;
import com.neroarc.blog.myblog.model.Image;
import com.neroarc.blog.myblog.service.ImageService;
import com.neroarc.blog.myblog.utils.FileUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: fjx
 * @date: 2019/3/6 21:16
 * Descripe:
 */

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

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


    @Override
    public List<Image> searchImageByEs(String search) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        NativeSearchQuery query = queryBuilder.withQuery(QueryBuilders.multiMatchQuery(search,"tag","description"))
                .withHighlightFields(new HighlightBuilder.Field("tag").preTags("<span style=\"color:red\">").postTags("</span>")).build();

        AggregatedPage<Image> images = elasticsearchTemplate.queryForPage(query, Image.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Image> imageTemp = new ArrayList<>();
                SearchHits hits = searchResponse.getHits();

                for (SearchHit hit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }

                    Image image = new Image();
                    image.setId(Integer.parseInt(hit.getId()));
                    image.setUrl(String.valueOf(hit.getSource().get("url")));
                    image.setDescription(String.valueOf(hit.getSource().get("description")));
                    image.setType(Integer.parseInt(String.valueOf(hit.getSource().get("type"))));
                    image.setTag(String.valueOf(hit.getSource().get("tag")));

                    //防止标签不存在，描述存在的问题
                    if (hit.getHighlightFields().size()!=0) {
                        String highlightString = hit.getHighlightFields().get("tag").fragments()[0].toString();
                        image.setTag(highlightString);
                    }
                    imageTemp.add(image);
                }
                return new AggregatedPageImpl<>((List<T>)imageTemp);
            }
        });


        List<Image> imagesContent = images.getContent();
        return imagesContent;
    }


    @Override
    public Map<String,Object> getPageBgImage(int rows, int pageNum) {
        PageHelper.startPage(pageNum,rows);
        List<Image> images = imageMapper.getBgAllImages();

        if (images.size() == 0) {
            Map<String, Object> empty = new HashMap<>(1);
            empty.put("status", 404);
            return empty;
        }

        Map<String,Object> returnMap = new HashMap<>(images.size()+4);
        PageInfo<Image> pageInfo = new PageInfo<>(images);
        returnMap.put("result",images);
        returnMap.put("totalPage",pageInfo.getPages());
        returnMap.put("totalSize",pageInfo.getTotal());
        returnMap.put("num",pageInfo.getPageNum());
        returnMap.put("status", 200);
        return returnMap;
    }

    @Override
    public int updateBgImage(Image image) {
        return imageMapper.updateBgImage(image);
    }
}
