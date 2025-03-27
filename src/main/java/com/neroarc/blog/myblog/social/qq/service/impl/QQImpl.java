package com.neroarc.blog.myblog.social.qq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neroarc.blog.myblog.mapper.UserMapper;
import com.neroarc.blog.myblog.model.QQUser;
import com.neroarc.blog.myblog.model.User;
import com.neroarc.blog.myblog.social.qq.service.QQ;
import com.neroarc.blog.myblog.utils.DateUtil;
import com.neroarc.blog.myblog.utils.SpringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: fjx
 * @date: 2019/1/24 14:47
 * Descripe:
 */

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /**
     * 注意，下面的两个链接都需要access_token，只是统一在interceptor添加了
     */
    private static final String QQ_URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me";
    private static final String QQ_URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private final String appId;
    private final String openId;

    private final UserMapper userMapper = SpringUtils.getApplicationContext().getBean(UserMapper.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        // 默认加入的是ISO-8859-1编码，需要修改为UTF-8编码
        this.getRestTemplate().getMessageConverters().removeIf(messageConverter -> messageConverter instanceof StringHttpMessageConverter);
        this.getRestTemplate().getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        this.appId = appId;
        // 这里请求的会带access_token，template使用interceptor处理的，不用手工添加
        String result = getRestTemplate().getForObject(QQ_URL_GET_OPENID, String.class);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    /**
     * TODO(ricefan): 写的太垃圾了，根本不应该在这里面更新的，以后改记得移出去
     */
    @Override
    public QQUser getUserInfo() {
        QQUser userInfo = doGetUserInfo();
        saveSystemUser(userInfo);
        return userInfo;
    }

    private QQUser doGetUserInfo() {
        String url = String.format(QQ_URL_GET_USER_INFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        QQUser qqUser;
        try {
            qqUser = objectMapper.readValue(result, QQUser.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("doGetUserInfo transform result failed");
        }

        qqUser.setOpenId(openId);
        return qqUser;
    }

    private void saveSystemUser(QQUser qqUser) {
        User user = new User();
        user.setProviderId(openId);
        user.setRecentLoginDate(DateUtil.getStringTime());
        user.setName(qqUser.getNickname());
        user.setImageUrl(qqUser.getFigureurl_qq_2());

        if (userMapper.isUserExist(openId) == 0) {
            int gender = qqUser.getGender().equals("男") ? 1 : 0;
            user.setPassword("");
            user.setGender(gender);
            user.setRole("ROLE_USER");
            userMapper.addUser(user);
        } else {
            User existUser = userMapper.getUserByProviderId(openId);
            if (existUser.getName() != null && existUser.getName().equals(qqUser.getNickname())) {
                user.setName(null);
            }

            if (existUser.getImageUrl() != null && existUser.getImageUrl().equals(qqUser.getFigureurl_qq_2())) {
                user.setImageUrl(null);
            }
        }

        userMapper.selectiveUpdateUser(user);
    }
}
