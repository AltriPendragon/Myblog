package com.neroarc.blog.myblog.service.impl;

import com.neroarc.blog.myblog.mapper.QQMapper;
import com.neroarc.blog.myblog.mapper.UserMapper;
import com.neroarc.blog.myblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author: fjx
 * @date: 2019/1/24 16:44
 * Descripe:
 */

@Component
public class QQUserDetailsService implements SocialUserDetailsService {

    @Autowired
    QQMapper qqMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
        return buildUser(s);
    }


    private SocialUser buildUser(String userId) {
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        String providerId = qqMapper.getProviderIdByName(userId);
        User user = userMapper.getUserByProviderId(providerId);
        String password = "";
        return new SocialUser(Long.toString(user.getId()), password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
    }
}
