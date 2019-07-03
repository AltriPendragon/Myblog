package com.neroarc.blog.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neroarc.blog.myblog.mapper.AdminMapper;
import com.neroarc.blog.myblog.mapper.ArticleMapper;
import com.neroarc.blog.myblog.mapper.UserMapper;
import com.neroarc.blog.myblog.model.Comment;
import com.neroarc.blog.myblog.model.LeaveMessage;
import com.neroarc.blog.myblog.model.User;
import com.neroarc.blog.myblog.service.AdminService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/9 19:14
 * Descripe:
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getNotReadingComment(int rows,int pageNum) {
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        PageHelper.startPage(pageNum,rows);
        List<Comment> comments = adminMapper.getNotReadingComment();
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        if(comments.size()>0){
            for(Comment comment:comments){
                String title = articleMapper.getArticleNameByArticleId(comment.getArticleId());
                User user = userMapper.getUserById(comment.getRemarkerId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("articleId",comment.getArticleId());
                jsonObject.put("commentId",comment.getCommentId());
                jsonObject.put("title",title);
                jsonObject.put("name",user.getName());
                jsonObject.put("content",comment.getCommentContent());
                jsonObject.put("time",comment.getCommentDate());
                jsonArray.add(jsonObject);
            }

            returnJson.put("result",jsonArray);
            returnJson.put("num",pageInfo.getPageNum());
            returnJson.put("totalPage",pageInfo.getPages());
            returnJson.put("totalSize",pageInfo.getTotal());
            returnJson.put("status",200);

            return returnJson;
        }

        returnJson.put("status",400);
        return returnJson;

    }

    @Override
    public JSONObject getNotReadingLeaveMessage(int rows,int pageNum) {
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        PageHelper.startPage(pageNum,rows);
        List<LeaveMessage> leaveMessages = adminMapper.getNotReadingLeaveMessage();
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(leaveMessages);

        if(leaveMessages.size()>0){
            for(LeaveMessage leaveMessage:leaveMessages){
                User user = userMapper.getUserById(leaveMessage.getRemarkerId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("leaveMessageId",leaveMessage.getMessageId());
                jsonObject.put("content",leaveMessage.getMessage());
                jsonObject.put("time",leaveMessage.getMessageDate());
                jsonObject.put("name",user.getName());
                jsonArray.add(jsonObject);
            }

            returnJson.put("result",jsonArray);
            returnJson.put("totalSize",pageInfo.getTotal());
            returnJson.put("totalPage",pageInfo.getPages());
            returnJson.put("num",pageInfo.getPageNum());
            returnJson.put("status",200);

            return returnJson;
        }

        returnJson.put("status",400);
        return returnJson;
    }

    @Override
    public JSONObject getNotReadingCommentCount() {
        JSONObject jsonObject = new JSONObject();
        int count = adminMapper.getNotReadingCommentCount();
        jsonObject.put("count",count);
        return jsonObject;
    }

    @Override
    public int updateCommentRead(long commentId) {
        return adminMapper.updateCommentRead(commentId);
    }

    @Override
    public JSONObject getNotReadingLeaveMessageCount() {
        JSONObject jsonObject = new JSONObject();
        int count = adminMapper.getNotReadingLeaveMessageCount();
        jsonObject.put("count",count);
        return jsonObject;
    }

    @Override
    public int updateMessageRead(long messageId) {
        return adminMapper.updateMessageRead(messageId);
    }

    @Override
    public JSONObject getAllCounts() {
        JSONObject jsonObject = new JSONObject();
        int commentCount = adminMapper.getNotReadingCommentCount();
        int messageCount = adminMapper.getNotReadingLeaveMessageCount();
        jsonObject.put("commentCount",commentCount);
        jsonObject.put("messageCount",messageCount);
        return jsonObject;
    }

    @Override
    public int clearAllComment() {
        return adminMapper.clearAllComment();
    }

    @Override
    public int clearAllMessage() {
        return adminMapper.clearAllMessage();
    }
}
