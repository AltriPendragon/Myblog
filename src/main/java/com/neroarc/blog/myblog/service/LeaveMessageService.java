package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.LeaveMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: fjx
 * @date: 2019/5/5 19:05
 * Descripe:
 */

@Service
public interface LeaveMessageService {

    /**
     * 添加留言
     * @param leaveMessage
     * @return
     */
    JSONObject addLeaveMessage(LeaveMessage leaveMessage);

    /**
     * 获得留言板下的留言，并获得我点赞的记录
     * @param userId
     * @return
     */
    JSONObject getLeaveMessage(long userId);

}
