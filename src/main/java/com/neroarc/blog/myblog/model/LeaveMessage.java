package com.neroarc.blog.myblog.model;

import lombok.Data;

/**
 * @author: fjx
 * @date: 2019/5/5 18:43
 * Descripe:
 */
@Data
public class LeaveMessage {

    private long messageId;
    private long remarkerId;
    private long responsorId;
    private long pId;
    private String message;
    private int likes;
    private String messageDate;
    private int isRead;
}
