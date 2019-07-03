package com.neroarc.blog.myblog.model;

import lombok.Data;
import java.io.Serializable;

/**
 * @author: fjx
 * @date: 2019/1/22 20:04
 * Descripe:
 */

@Data
public class Article implements Serializable {

    private long articleId;
    private String author;
    private String createTime;
    private String title;
    private String summary;
    private String content;
    private String type;
    private String category;
    private String tags;
    private int read;
    private int comment;

    private String htmlStr;
}
