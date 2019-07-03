package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author: fjx
 * @date: 2019/1/22 21:09
 * Descripe:
 */

@Service
public interface UserService {

    /**
     * 根据id获得用户
     * @param id 用户编号
     * @return
     */
    User getUserById(long id);

    /**
     * 根据用户昵称获得用户
     * @param name 用户昵称
     * @return
     */
    User getUserByName(String name);

    /**
     * 根据providerId获得用户
     * @param id 用户获得的providerId
     * @return
     */
    User getUserByProviderId(String id);

    /**
     * 用户注册
     * @param user 用户信息
     * @param telephoneCode 验证码
     * @return
     */

    int register(User user,String telephoneCode);

    /**
     * 获得电话验证码
     * @param telephone 电话
     * @return
     */
    int getTelephoneCode(String telephone);

    /**
     * 根据用户编号获得头像
     * @param id 用户编号
     * @return
     */
    String getHeadImg(long id);


    JSONObject getMyComments(long id,int rows,int pageNum);

    /**
     * 获得回复我的评论
     * @param id 自己的id
     * @param rows 行
     * @param pageNum 页
     * @return
     */
    JSONObject getReplyComments(long id,int rows,int pageNum);


    /**
     * 获得@的评论
     * @param id 自己的id
     * @param rows 行
     * @param pageNum 页
     * @return
     */
    JSONObject getReferMineComments(long id,int rows,int pageNum);


    /**
     * 更新用户信息
     * @param user 新的用户信息
     * @return
     */
    int updateUserInfo(User user);

    /**
     * 根据电话判断用户是否存在
     * @param telephone
     * @return
     */
    int isUserExist(long telephone);

    /**
     * 根据用户姓名判断用户是否存在
     * @param name
     * @return
     */
    int isUserNameExist(String name);

    /**
     * 重置密码
     * @param user
     * @param telephoneCode
     * @return
     */
    int resetPassword(User user,String telephoneCode);

}
