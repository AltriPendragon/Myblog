package com.neroarc.blog.myblog.model;

import lombok.Data;


/**
 * @author: fjx
 * @date: 2019/1/22 19:56
 * Descripe:
 */

@Data
public class User {

    private long id;
    private String providerId;
    private String password;
    private String name;
    private int gender;
    private String info;
    private String imageUrl;
    private String role;
    private String recentLoginDate;

}
