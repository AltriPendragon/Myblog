package com.neroarc.blog.myblog.model;

import lombok.Data;

/**
 * @author: fjx
 * @date: 2019/6/19 21:19
 * Descripe:
 */

@Data
public class Link {
    private int id;
    private String name;
    private String introduce;
    private String url;
    private String headImg;

    /**
     * 状态：0 正常
     * 1 废弃
     */
    private int status;
}
