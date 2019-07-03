package com.neroarc.blog.myblog.social.qq.connect;


import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @author: fjx
 * @date: 2019/1/24 13:16
 * Descripe:
 */
public class QQAuth2Template extends OAuth2Template {
    public QQAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl){
        super(clientId,clientSecret, authorizeUrl, accessTokenUrl);

        setUseParametersForClientAuthentication(true);
    }



    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

        //响应 access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
        String responseString = this.getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseString,"&");

        /*从字符串中取出每个元素，从每个元素中被"="分割的响应参数和值中取出响应参数值，
        重新赋值给AccessGrant类的构造方法的参数中，创建AccessGrant实体并返回，
        并调用SocialAuthenticationToken的构造方法，将AccessGrant作为构造方法的参数，
        返回创建的SocialAuthenticationToken实体，
        最终SocialAuthenticationFilter调用attemptAuthentication方法获得了SocialAuthenticationToken，
        获得了服务提供商发送的令牌*/
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = Long.parseLong(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(accessToken,null,refreshToken,expiresIn);
    }


    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
