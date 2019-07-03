package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.model.LeaveMessage;
import com.neroarc.blog.myblog.model.LeaveMessageLikesRecord;
import com.neroarc.blog.myblog.service.LeaveMessageLikesRecordService;
import com.neroarc.blog.myblog.service.LeaveMessageService;
import com.neroarc.blog.myblog.utils.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author: fjx
 * @date: 2019/5/5 19:40
 * Descripe:
 */
@RestController
public class LeaveMessageControl {


    @Autowired
    private LeaveMessageService leaveMessageService;
    
    @Autowired
    private LeaveMessageLikesRecordService leaveMessageLikesRecordService;

    @RequestMapping("/addLeaveMessage")
    public JSONObject addLeaveMessage(LeaveMessage leaveMessage,@AuthenticationPrincipal Principal principal){
        leaveMessage.setRemarkerId(Integer.parseInt(principal.getName()));
        leaveMessage.setMessageDate(DateUtil.getStringTime());
        return leaveMessageService.addLeaveMessage(leaveMessage);
    }

    @RequestMapping("/getLeaveMessage")
    public JSONObject getLeaveMessage(@AuthenticationPrincipal Principal principal){
        long userId = -1;
        if(principal!=null){
            userId = Long.parseLong(principal.getName());
        }

        return leaveMessageService.getLeaveMessage(userId);
    }

    @RequestMapping("/messageLikeAction")
    public int commentLikeAction(LeaveMessageLikesRecord leaveMessageLikesRecord, @AuthenticationPrincipal Principal principal){
        leaveMessageLikesRecord.setLikerId(Long.parseLong(principal.getName()));
        leaveMessageLikesRecord.setDate(DateUtil.getStringTime());
        return leaveMessageLikesRecordService.leaveMessageLikeAction(leaveMessageLikesRecord);
    }
}
