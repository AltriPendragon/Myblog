package com.neroarc.blog.myblog.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author: fjx
 * @date: 2019/8/9 13:17
 * Descripe:
 */
@Data
@Document(indexName = "blog",type = "image")
public class Image {

    private int id;
    private String url;
    private int type;

    @Field(type = FieldType.String,analyzer = "ik_max_word")
    private String tag;

    @Field(type = FieldType.String,analyzer = "ik_max_word")
    private String description;
}
