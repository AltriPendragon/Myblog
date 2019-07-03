package com.neroarc.blog.myblog.service.impl;

import com.neroarc.blog.myblog.mapper.UserMapper;
import com.neroarc.blog.myblog.model.User;
import com.neroarc.blog.myblog.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fjx
 * @date: 2019/1/22 21:02
 * Descripe:
 */
public class CustomUserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userMapper.getUserByProviderId(s);


        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        user.setRecentLoginDate(DateUtil.getStringTime());
        userMapper.updateTime(user);

        return new org.springframework.security.core.userdetails.User(Long.toString(user.getId()),user.getPassword(),authorities);
    }
}
