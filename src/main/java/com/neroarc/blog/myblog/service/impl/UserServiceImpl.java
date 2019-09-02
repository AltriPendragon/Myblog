package com.neroarc.blog.myblog.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neroarc.blog.myblog.constant.OSSClientConstants;
import com.neroarc.blog.myblog.mapper.ArticleMapper;
import com.neroarc.blog.myblog.mapper.CommentMapper;
import com.neroarc.blog.myblog.mapper.UserMapper;
import com.neroarc.blog.myblog.model.Comment;
import com.neroarc.blog.myblog.model.User;
import com.neroarc.blog.myblog.service.UserService;
import com.neroarc.blog.myblog.utils.DateUtil;
import com.neroarc.blog.myblog.utils.RedisUtil;
import com.neroarc.blog.myblog.utils.SmsUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: fjx
 * @date: 2019/1/22 21:16
 * Descripe:
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public User getUserById(long id) {
        return userMapper.getUserById(id);
    }


    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public User getUserByProviderId(String id) {
        return userMapper.getUserByProviderId(id);
    }

    @Override
    public int register(User user,String telephoneCode) {
        if(RedisUtil.hasKey(user.getProviderId())==true){
            String code = RedisUtil.getValue(user.getProviderId());
            if(code.equals(telephoneCode)==true){
                user.setRole("ROLE_USER");
                user.setRecentLoginDate(DateUtil.getStringTime());
                user.setImageUrl(OSSClientConstants.DEFAULT_IMG);
                userMapper.addUser(user);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int getTelephoneCode(String telephone) {

        int code = (int)(Math.random()*9000)+1000;
        int flag = 1;
        try {
            SendSmsResponse sendSmsResponse = SmsUtils.sendSms(telephone,Integer.toString(code));
            RedisUtil.putValue(telephone,Integer.toString(code),300);
        } catch (ClientException e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }


    @Override
    public String getHeadImg(long id) {
        return userMapper.getHeadImg(id);
    }


    /**
     * 得到我发表的评论（未使用）
     * @param id
     * @param rows
     * @param pageNum
     * @return
     */
    @Override
    public JSONObject getMyComments(long id,int rows,int pageNum) {
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        PageHelper.startPage(pageNum,rows);
        List<Comment> comments = commentMapper.getCommentsOfUser(id);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        if(comments.size()>0){
            jsonArray.add(JSONArray.fromObject(comments));
            returnJson.put("result",jsonArray);
            returnJson.put("status",200);
            returnJson.put("totalPage",pageInfo.getPages());
            returnJson.put("totalSize",pageInfo.getTotal());
            returnJson.put("num",pageInfo.getPageNum());
            return returnJson;
        }

        returnJson.put("status",400);
        return returnJson;
    }


    /**
     * 获得回复我的评论
     * @param id
     * @param rows
     * @param pageNum
     * @return
     */
    @Override
    public JSONObject getReplyComments(long id, int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Comment> comments = new ArrayList<>();
        List<String> parentReplyCommentsId = commentMapper.getParentReplyCommentsId(id);

        PageHelper.startPage(pageNum,rows);
        if(parentReplyCommentsId.size()>0){
            comments = commentMapper.getChildrenReplyComments(parentReplyCommentsId);
        }


        if(comments.size()>0){
            PageInfo<Comment> pageInfo = new PageInfo<>(comments);
            for(Comment comment:comments){

                User user = userMapper.getUserById(comment.getRemarkerId());
                String title = articleMapper.getArticleNameByArticleId(comment.getArticleId());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("time",comment.getCommentDate());
                jsonObject.put("commentId",comment.getCommentId());
                jsonObject.put("articleId",comment.getArticleId());
                jsonObject.put("content",comment.getCommentContent());

                jsonObject.put("title",title);
                jsonObject.put("name",user.getName());
                jsonArray.add(jsonObject);
            }


            returnJson.put("status",200);
            returnJson.put("result",jsonArray);
            returnJson.put("totalSize",pageInfo.getTotal());
            returnJson.put("totalPage",pageInfo.getPages());
            returnJson.put("num",pageInfo.getPageNum());
            return returnJson;
        }

        returnJson.put("status",400);
        return returnJson;
    }


    /**
     * 获得@我的评论
     * @param id
     * @param rows
     * @param pageNum
     * @return
     */
    @Override
    public JSONObject getReferMineComments(long id, int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        PageHelper.startPage(pageNum,rows);
        List<Comment> comments = commentMapper.getReferMineComments(id);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        if(comments.size()>0){

            for(Comment comment:comments){
                String title = articleMapper.getArticleNameByArticleId(comment.getArticleId());
                User user = userMapper.getUserById(comment.getRemarkerId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("commentId",comment.getCommentId());
                jsonObject.put("articleId",comment.getArticleId());
                jsonObject.put("content",comment.getCommentContent());
                jsonObject.put("time",comment.getCommentDate());
                jsonObject.put("name",user.getName());
                jsonObject.put("title",title);
                jsonArray.add(jsonObject);
            }

            returnJson.put("result",jsonArray);
            returnJson.put("totalPage",pageInfo.getPages());
            returnJson.put("totalSize",pageInfo.getTotal());
            returnJson.put("num",pageInfo.getPageNum());
            returnJson.put("status",200);

            return returnJson;
        }

        returnJson.put("status",400);
        return returnJson;
    }

    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateUserInfo(user);
    }

    @Override
    public int isUserExist(long telephone) {
        return userMapper.isUserExist(Long.toString(telephone));
    }

    @Override
    public int resetPassword(User user, String telephoneCode) {
        if(RedisUtil.hasKey(user.getProviderId())==true){
            String code = RedisUtil.getValue(user.getProviderId());
            if(code.equals(telephoneCode)==true){
                userMapper.updatePassword(user);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int isUserNameExist(String name) {
        userMapper.isUserNameExist(name);
        
        return this.userMapper.isUserNameExist(name);
    }
}
