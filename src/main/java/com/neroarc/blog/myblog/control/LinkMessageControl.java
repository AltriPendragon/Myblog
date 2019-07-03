package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.model.LinkMessage;
import com.neroarc.blog.myblog.model.LinkMessageLikesRecord;
import com.neroarc.blog.myblog.service.LinkMessageLikesRecordService;
import com.neroarc.blog.myblog.service.LinkMessageService;
import com.neroarc.blog.myblog.utils.DateUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author: fjx
 * @date: 2019/5/25 19:39
 * Descripe:
 */
@RestController
public class LinkMessageControl {

    @Autowired
    private LinkMessageService linkMessageService;
    
    @Autowired
    private LinkMessageLikesRecordService linkMessageLikesRecordService;


    @RequestMapping("/addLinkMessage")
    public JSONObject addLeaveMessage(LinkMessage link, @AuthenticationPrincipal Principal principal){
        link.setRemarkerId(Integer.parseInt(principal.getName()));
        link.setMessageDate(DateUtil.getStringTime());
        return linkMessageService.addLinkMessage(link);
    }

    @RequestMapping("/getLinkMessage")
    public JSONObject getLeaveMessage(@AuthenticationPrincipal Principal principal){
        long userId = -1;
        if(principal!=null){
            userId = Long.parseLong(principal.getName());
        }

        return linkMessageService.getLinkMessage(userId);
    }


    @RequestMapping("/linkLikeAction")
    public int commentLikeAction(LinkMessageLikesRecord linkMessageLikesRecord, @AuthenticationPrincipal Principal principal){
        linkMessageLikesRecord.setLikerId(Long.parseLong(principal.getName()));
        linkMessageLikesRecord.setDate(DateUtil.getStringTime());
        return linkMessageLikesRecordService.linkLikeAction(linkMessageLikesRecord);
    }
}
