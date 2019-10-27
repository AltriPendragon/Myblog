package com.neroarc.blog.myblog.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @author: fjx
 * @date: 2018/9/20 18:23
 * Descripe:解决在新的线程中无法自动注入的问题
 */

@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T)applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        return (T)applicationContext.getBean(clz);
    }

}
