package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.LinkMessage;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: fjx
 * @date: 2019/5/25 19:40
 * Descripe:
 */
@Service
public interface LinkMessageService {

    /**
     * 添加友链留言
     * @param linkMessage
     * @return
     */
    JSONObject addLinkMessage(LinkMessage linkMessage);

    /**
     * 获得友链留言，并根据userId获得我点赞的留言
     * @param userId
     * @return
     */
    JSONObject getLinkMessage(long userId);
}
