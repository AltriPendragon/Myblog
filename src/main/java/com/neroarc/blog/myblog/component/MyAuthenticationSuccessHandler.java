package com.neroarc.blog.myblog.component;

import com.neroarc.blog.myblog.constant.StringConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: fjx
 * @date: 2018/9/20 14:40
 * Descripe:
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        if(request.getSession().getAttribute(StringConstants.STRING_CONSTANT_URL)!=null){

            if(((String)request.getSession().getAttribute(StringConstants.STRING_CONSTANT_URL)).contains(StringConstants.STRING_CONSTANT_LOGIN)){
                super.onAuthenticationSuccess(request, response, authentication);
            }

            //如果是要跳转到某个页面的
            else {
                new DefaultRedirectStrategy().sendRedirect(request, response,(String)request.getSession().getAttribute(StringConstants.STRING_CONSTANT_URL));
                request.getSession().removeAttribute(StringConstants.STRING_CONSTANT_URL);
            }

        }

        else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}

