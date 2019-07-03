package com.neroarc.blog.myblog.service.impl;

import com.neroarc.blog.myblog.mapper.LeaveMessageLikesRecordMapper;
import com.neroarc.blog.myblog.mapper.LeaveMessageMapper;
import com.neroarc.blog.myblog.model.LeaveMessageLikesRecord;
import com.neroarc.blog.myblog.service.LeaveMessageLikesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/25 20:44
 * Descripe:
 */

@Service
public class LeaveMessageLikesRecordImpl implements LeaveMessageLikesRecordService {

    @Autowired
    private LeaveMessageMapper leaveMessageMapper;
    
    @Autowired
    private LeaveMessageLikesRecordMapper leaveMessageLikesRecordMapper;
    
    @Override
    public int leaveMessageLikeAction(LeaveMessageLikesRecord leaveMessageLikesRecord) {
        int flag = leaveMessageLikesRecordMapper.isMessageRecordExist(leaveMessageLikesRecord);
        if(flag==1){
            if(leaveMessageLikesRecordMapper.getFlagByOthers(leaveMessageLikesRecord)==1){
                leaveMessageLikesRecordMapper.cancelLikeMessage(leaveMessageLikesRecord);
                leaveMessageMapper.decreaseMessageLike(leaveMessageLikesRecord.getMessageId());
                return 0;
            }

            else {
                leaveMessageLikesRecordMapper.likeMessage(leaveMessageLikesRecord);
                leaveMessageMapper.increaseMessageLike(leaveMessageLikesRecord.getMessageId());
                return 1;
            }
        }

        leaveMessageLikesRecordMapper.addLikeMessage(leaveMessageLikesRecord);
        leaveMessageMapper.increaseMessageLike(leaveMessageLikesRecord.getMessageId());
        return 1;
    }

    @Override
    public List<String> getOwnLeaveMessageLikeId(long userId) {
        List<String> commentIds = leaveMessageLikesRecordMapper.getOwnLeaveMessageLikeId(userId);
        return commentIds;
    }
}
