package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.LeaveMessageLikesRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/25 20:41
 * Descripe:
 */
@Service
public interface LeaveMessageLikesRecordService {

    /**
     * 留言板下点赞
     * @param leaveMessageLikesRecord
     * @return
     */
    int leaveMessageLikeAction(LeaveMessageLikesRecord leaveMessageLikesRecord);

    /**
     * 获得我点赞的留言
     * @param userId
     * @return
     */
    List<String> getOwnLeaveMessageLikeId(long userId);
}
