package com.neroarc.blog.myblog.social.qq.config;

import com.neroarc.blog.myblog.constant.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author: fjx
 * @date: 2019/1/24 15:49
 * Descripe:
 */

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {


    @Bean
    public SpringSocialConfigurer qqSocialSecurityConfig(){

        String filterProcessUrl = SecurityConstants.DEFAULT_SOCIAL_QQ_PROCESS_URL;

        QQSpringSocialConfigurer qqSpringSocialConfigurer = new QQSpringSocialConfigurer(filterProcessUrl);

        return qqSpringSocialConfigurer;
    }



    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator factoryLocator) {
        return new ProviderSignInUtils(factoryLocator, getUsersConnectionRepository(factoryLocator));
    }
}
