package com.neroarc.blog.myblog.social.qq.connect;

import com.neroarc.blog.myblog.social.qq.service.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author: fjx
 * @date: 2019/1/24 15:21
 * Descripe:
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId, String appSecret){
        super(providerId,new QQServiceProvider(appId,appSecret),new QQAdapter());
    }
}
