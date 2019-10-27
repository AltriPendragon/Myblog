package com.neroarc.blog.myblog.config;

import com.neroarc.blog.myblog.service.impl.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;

/**
 * @author: fjx
 * @date: 2019/1/22 21:28
 * Descripe:
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    @Autowired
    private SpringSocialConfigurer qqSpringSocialConfigurer;

    @Autowired
    @Qualifier("myAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler authenticationSuccessHandler;




    @Override

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/login").successHandler(authenticationSuccessHandler).failureUrl("/login").permitAll()
//                .loginProcessingUrl("/sign")
                .and()
//                .sessionManagement()
//                .invalidSessionUrl("/session/invalid")
//                .and()
                .authorizeRequests()
                .antMatchers("/","/test","/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/index","/editTest","/*")
                .permitAll()
                .and()
                .apply(qqSpringSocialConfigurer);
       http.csrf().disable();



    }
}
