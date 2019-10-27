package com.neroarc.blog.myblog.social.qq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neroarc.blog.myblog.mapper.UserMapper;
import com.neroarc.blog.myblog.model.QQUser;
import com.neroarc.blog.myblog.model.User;
import com.neroarc.blog.myblog.social.qq.service.QQ;
import com.neroarc.blog.myblog.utils.DateUtil;
import com.neroarc.blog.myblog.utils.SpringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @author: fjx
 * @date: 2019/1/24 14:47
 * Descripe:
 */

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String QQ_URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String QQ_URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;
    private String openId;


    UserMapper userMapper = SpringUtils.getApplicationContext().getBean(UserMapper.class);


    private ObjectMapper objectMapper = new ObjectMapper();
    User user = new User();

    public QQImpl(String accessToken,String appId){
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        String url = String.format(QQ_URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUser getUserInfo() {
        String url = String.format(QQ_URL_GET_USER_INFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);


        QQUser userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userInfo.setOpenId(openId);


        user.setProviderId(openId);
        user.setRecentLoginDate(DateUtil.getStringTime());


        if(userMapper.isUserExist(openId)==0){
            int gender = userInfo.getGender().equals("男")?1:0;
            user.setName(userInfo.getNickname());
            user.setPassword("");
            user.setGender(gender);
            user.setImageUrl(userInfo.getFigureurl_qq_2());
            user.setRole("ROLE_USER");

            userMapper.addUser(user);
        }

        userMapper.updateTime(user);
        return userInfo;

    }
}
