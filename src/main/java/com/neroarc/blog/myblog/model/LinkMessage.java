package com.neroarc.blog.myblog.model;

import lombok.Data;

/**
 * @author: fjx
 * @date: 2019/5/25 19:41
 * Descripe:
 */

@Data
public class LinkMessage {

    /**
     * link留言
     */
    private long linkId;
    private long remarkerId;
    private long responsorId;
    private long pId;
    private String message;
    private int likes;
    private String messageDate;
    private int isRead;
}
