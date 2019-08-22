package com.neroarc.blog.myblog.model;

import lombok.Data;

/**
 * @author: fjx
 * @date: 2019/8/9 13:17
 * Descripe:
 */
@Data
public class Image {

    private int id;
    private String url;
    private int type;
    private String tag;
    private String description;
}
