package com.neroarc.blog.myblog.model;

import lombok.Data;

/**
 * @author: fjx
 * @date: 2019/5/25 19:41
 * Descripe:
 */
@Data
public class LinkMessageLikesRecord {

    private long recordId;

    private long linkId;

    private long likerId;

    /**
     * 1:点赞
     * 0：取消点赞
     */
    private int flag;

    private String date;
}
