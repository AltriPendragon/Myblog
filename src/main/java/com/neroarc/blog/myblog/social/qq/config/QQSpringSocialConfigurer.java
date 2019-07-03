package com.neroarc.blog.myblog.social.qq.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author: fjx
 * @date: 2019/1/24 16:09
 * Descripe:
 */



public class QQSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public QQSpringSocialConfigurer(String filterProcessesUrl){
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        filter.setSignupUrl("/");
        return (T) filter;
    }
}
