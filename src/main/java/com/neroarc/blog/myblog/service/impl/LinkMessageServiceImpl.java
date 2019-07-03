package com.neroarc.blog.myblog.service.impl;

import com.neroarc.blog.myblog.mapper.LinkMessageLikesRecordMapper;
import com.neroarc.blog.myblog.mapper.LinkMessageMapper;
import com.neroarc.blog.myblog.mapper.UserMapper;
import com.neroarc.blog.myblog.model.LinkMessage;
import com.neroarc.blog.myblog.model.User;
import com.neroarc.blog.myblog.service.LinkMessageService;
import com.neroarc.blog.myblog.utils.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/25 19:48
 * Descripe:
 */

@Service
public class LinkMessageServiceImpl implements LinkMessageService {

    @Autowired
    private LinkMessageMapper linkMessageMapper;

    @Autowired
    private LinkMessageLikesRecordMapper linkMessageLikesRecordMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject addLinkMessage(LinkMessage linkMessage) {
        JSONObject jsonObject = new JSONObject();
        int flag = linkMessageMapper.addLinkMessage(linkMessage);
        if(flag==1){
            long responsorId = linkMessage.getResponsorId();

            //不包括endindex,即要加一
            jsonObject.put("date", DateUtil.getStringTime().substring(0,16));
            if(responsorId!=0){
                jsonObject.put("responsorName",userMapper.getUserById(responsorId).getName());
            }

            jsonObject.put("status",200);
            jsonObject.put("remarkerName",userMapper.getUserById(linkMessage.getRemarkerId()).getName());
            jsonObject.put("pid",linkMessage.getLinkId());
            return jsonObject;
        }

        jsonObject.put("status",500);
        return jsonObject;
    }

    @Override
    public JSONObject getLinkMessage(long userId) {
        int linkNum = 0;
        List<String> ownLinkMessageLikeId = new ArrayList<>();
        JSONArray parentJsonArray = new JSONArray();
        JSONObject returnJson = new JSONObject();

        if(userId!=-1){
            ownLinkMessageLikeId = linkMessageLikesRecordMapper.getOwnLeaveMessageLikeId(userId);
        }

        List<LinkMessage> parents = linkMessageMapper.getParentMessage();
        for(LinkMessage linkMessage:parents){
            JSONArray childJsonArray = new JSONArray();
            linkNum++;
            User user = userMapper.getUserById(linkMessage.getRemarkerId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("messageId",linkMessage.getLinkId());
            jsonObject.put("message",linkMessage.getMessage());
            jsonObject.put("date",linkMessage.getMessageDate());
            jsonObject.put("likes",linkMessage.getLikes());
            jsonObject.put("avatarImgUrl",user.getImageUrl());
            jsonObject.put("userName",user.getName());
            jsonObject.put("isLike",0);
            if(ownLinkMessageLikeId.size()!=0){
                if(ownLinkMessageLikeId.contains(Long.toString(linkMessage.getLinkId()))){
                    jsonObject.put("isLike",1);
                }
            }

            List<LinkMessage> children = linkMessageMapper.getChildrenMessage(linkMessage.getLinkId());
            for(LinkMessage child:children){
                linkNum++;
                JSONObject childJson = new JSONObject();
                User childUser = userMapper.getUserById(child.getRemarkerId());
                if(child.getResponsorId()!=0){
                    childJson.put("responsorName",userMapper.getUserById(child.getResponsorId()).getName());
                }

                childJson.put("responsorId",child.getResponsorId());
                childJson.put("messageId",child.getLinkId());
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
        returnJson.put("linkNum",linkNum);
        return returnJson;
    }

}
