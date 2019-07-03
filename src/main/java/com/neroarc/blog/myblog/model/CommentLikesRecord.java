package com.neroarc.blog.myblog.model;

import lombok.Data;

/**
 * @author: fjx
 * @date: 2019/4/23 22:06
 * Descripe:
 */

@Data
public class CommentLikesRecord {

    private long recordId;

    private long articleId;

    private long commentId;

    private long likerId;

    /**
     * 1:点赞
     * 0：取消点赞
     */
    private int flag;

    private String date;
}
