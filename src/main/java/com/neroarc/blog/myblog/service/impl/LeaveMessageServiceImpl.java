package com.neroarc.blog.myblog.service.impl;

import com.neroarc.blog.myblog.mapper.LeaveMessageLikesRecordMapper;
import com.neroarc.blog.myblog.mapper.LeaveMessageMapper;
import com.neroarc.blog.myblog.mapper.UserMapper;
import com.neroarc.blog.myblog.model.LeaveMessage;
import com.neroarc.blog.myblog.model.User;
import com.neroarc.blog.myblog.service.LeaveMessageService;
import com.neroarc.blog.myblog.utils.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/5 19:08
 * Descripe:
 */

@Service
public class LeaveMessageServiceImpl implements LeaveMessageService {

    @Autowired
    private LeaveMessageMapper leaveMessageMapper;

    @Autowired
    private LeaveMessageLikesRecordMapper leaveMessageLikesRecordMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject addLeaveMessage(LeaveMessage leaveMessage) {
        JSONObject jsonObject = new JSONObject();
        int flag = leaveMessageMapper.addLeaveMessage(leaveMessage);
        if(flag==1){
            long responsorId = leaveMessage.getResponsorId();

            //不包括endindex,即要加一
            jsonObject.put("date", DateUtil.getStringTime().substring(0,16));
            if(responsorId!=0){
                jsonObject.put("responsorName",userMapper.getUserById(responsorId).getName());
            }

            jsonObject.put("status",200);
            jsonObject.put("remarkerName",userMapper.getUserById(leaveMessage.getRemarkerId()).getName());
            jsonObject.put("pid",leaveMessage.getMessageId());
            return jsonObject;
        }

        jsonObject.put("status",500);
        return jsonObject;
    }

    @Override
    public JSONObject getLeaveMessage(long userId) {
        int messageNum = 0;
        List<String> ownLeaveMessageLikeId = new ArrayList<>();
        JSONArray parentJsonArray = new JSONArray();
        JSONObject returnJson = new JSONObject();

        if(userId!=-1){
            ownLeaveMessageLikeId = leaveMessageLikesRecordMapper.getOwnLeaveMessageLikeId(userId);
        }

        List<LeaveMessage> parents = leaveMessageMapper.getParentMessage();
        for(LeaveMessage leaveMessage:parents){
            JSONArray childJsonArray = new JSONArray();
            messageNum++;
            User user = userMapper.getUserById(leaveMessage.getRemarkerId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("messageId",leaveMessage.getMessageId());
            jsonObject.put("message",leaveMessage.getMessage());
            jsonObject.put("date",leaveMessage.getMessageDate());
            jsonObject.put("likes",leaveMessage.getLikes());
            jsonObject.put("avatarImgUrl",user.getImageUrl());
            jsonObject.put("userName",user.getName());
            jsonObject.put("isLike",0);
            if(ownLeaveMessageLikeId.size()!=0){
                if(ownLeaveMessageLikeId.contains(Long.toString(leaveMessage.getMessageId()))){
                    jsonObject.put("isLike",1);
                }
            }

            List<LeaveMessage> children = leaveMessageMapper.getChildrenMessage(leaveMessage.getMessageId());
            for(LeaveMessage child:children){
                messageNum++;
                JSONObject childJson = new JSONObject();
                User childUser = userMapper.getUserById(child.getRemarkerId());
                if(child.getResponsorId()!=0){
                    childJson.put("responsorName",userMapper.getUserById(child.getResponsorId()).getName());
                }

                childJson.put("responsorId",child.getResponsorId());
                childJson.put("messageId",child.getMessageId());
                childJson.put("remarkerId",child.getRemarkerId());
                childJson.put("message",child.getMessage());
                childJson.put("date",child.getMessageDate());
                childJson.put("avatarImgUrl",childUser.getImageUrl());
                childJson.put("userName",childUser.getName());
                childJsonArray.add(childJson);
            }

            jsonObject.put("replies",childJsonArray);
            parentJsonArray.add(jsonObject);
        }

        returnJson.put("result",parentJsonArray);
        returnJson.put("messageNum",messageNum);
        return returnJson;
    }

}
