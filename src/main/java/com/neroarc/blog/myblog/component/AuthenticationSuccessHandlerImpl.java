package com.neroarc.blog.myblog.component;

import com.neroarc.blog.myblog.constant.StringConstants;
import com.neroarc.blog.myblog.mapper.UserMapper;
import com.neroarc.blog.myblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author: fjx
 * @date: 2019/4/25 19:41
 * Descripe:
 */

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        if(httpServletRequest.getSession().getAttribute(StringConstants.STRING_CONSTANT_URL)!=null){
            httpServletResponse.sendRedirect((String)httpServletRequest.getSession().getAttribute(StringConstants.STRING_CONSTANT_URL));
            httpServletRequest.getSession().removeAttribute(StringConstants.STRING_CONSTANT_URL);
        }

        else {
            httpServletRequest.getRequestDispatcher("/").forward(httpServletRequest, httpServletResponse);
        }
    }

}
