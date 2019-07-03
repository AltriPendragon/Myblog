package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.model.User;
import com.neroarc.blog.myblog.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fjx
 * @date: 2019/4/24 15:24
 * Descripe:
 */
@RestController
public class AndroidLoginControl {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;


    @RequestMapping("/androidLogin")
    public int doLogin(@RequestParam("telephone") String telephone, @RequestParam("password") String password){
        User user = userService.getUserByProviderId(telephone);
        if(user==null){
            throw new UsernameNotFoundException("没有当前用户");
        }

        else {
            if(user.getPassword().equals(password)){
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getProviderId(), password, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
                Authentication authentication = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return 1;
            }
        }


        return 0;
    }

}
