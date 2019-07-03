package com.neroarc.blog.myblog.service.impl;

import com.neroarc.blog.myblog.mapper.LinkMessageLikesRecordMapper;
import com.neroarc.blog.myblog.mapper.LinkMessageMapper;
import com.neroarc.blog.myblog.model.LinkMessageLikesRecord;
import com.neroarc.blog.myblog.service.LinkMessageLikesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/25 20:45
 * Descripe:
 */
@Service
public class LinkMessageLikesRecordImpl implements LinkMessageLikesRecordService {
    
    @Autowired
    private LinkMessageMapper linkMessageMapper;
    
    @Autowired
    private LinkMessageLikesRecordMapper linkMessageLikesRecordMapper;
    
    @Override
    public int linkLikeAction(LinkMessageLikesRecord linkMessageLikesRecord) {
        int flag = linkMessageLikesRecordMapper.isLinkRecordExist(linkMessageLikesRecord);
        if(flag==1){
            if(linkMessageLikesRecordMapper.getFlagByOthers(linkMessageLikesRecord)==1){
                linkMessageLikesRecordMapper.cancelLikeLinkMessage(linkMessageLikesRecord);
                linkMessageMapper.decreaseMessageLike(linkMessageLikesRecord.getLinkId());
                return 0;
            }

            else {
                linkMessageLikesRecordMapper.likeLinkMessage(linkMessageLikesRecord);
                linkMessageMapper.increaseMessageLike(linkMessageLikesRecord.getLinkId());
                return 1;
            }
        }

        linkMessageLikesRecordMapper.addLikeLinkMessage(linkMessageLikesRecord);
        linkMessageMapper.increaseMessageLike(linkMessageLikesRecord.getLinkId());
        return 1;
    }

    @Override
    public List<String> getOwnLinkLikeId(long userId) {
        List<String> commentIds = linkMessageLikesRecordMapper.getOwnLeaveMessageLikeId(userId);
        return commentIds;
    }
}
