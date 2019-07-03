package com.neroarc.blog.myblog.model;

import lombok.Data;

/**
 * @author: fjx
 * @date: 2019/5/5 19:17
 * Descripe:
 */
@Data
public class LeaveMessageLikesRecord {
    private long recordId;

    private long messageId;

    private long likerId;

    /**
     * 1:点赞
     * 0：取消点赞
     */
    private int flag;

    private String date;
}
