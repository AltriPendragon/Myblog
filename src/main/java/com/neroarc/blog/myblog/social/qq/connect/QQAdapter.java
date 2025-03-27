package com.neroarc.blog.myblog.social.qq.connect;

import com.neroarc.blog.myblog.model.QQUser;
import com.neroarc.blog.myblog.social.qq.service.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author: fjx
 * @date: 2019/1/24 15:04
 * Descripe:
 */
public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        QQUser userInfo = qq.getUserInfo();

        // 这里设置的值在QQConnectionSignUp中能拿到，实际是设置到了Connection中
        connectionValues.setProviderUserId(userInfo.getOpenId());
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_2());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
