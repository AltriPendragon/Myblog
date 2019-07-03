package com.neroarc.blog.myblog.social.qq.connect;

import com.neroarc.blog.myblog.social.qq.service.QQ;
import com.neroarc.blog.myblog.social.qq.service.impl.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author: fjx
 * @date: 2019/1/24 13:09
 * Descripe:
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ>{

    private static final String QQ_URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    private static final String QQ_URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
    private String appId;

    public QQServiceProvider(String appId, String appSecret){
        super(new QQAuth2Template(appId, appSecret, QQ_URL_AUTHORIZE, QQ_URL_ACCESS_TOKEN));
        this.appId = appId;
    }


    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}
