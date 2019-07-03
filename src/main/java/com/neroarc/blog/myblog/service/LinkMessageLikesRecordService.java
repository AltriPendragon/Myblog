package com.neroarc.blog.myblog.service;

import com.neroarc.blog.myblog.model.LinkMessageLikesRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: fjx
 * @date: 2019/5/25 20:43
 * Descripe:
 */

@Service
public interface LinkMessageLikesRecordService {

    /**
     * 点赞+取消
     * @param linkMessageLikesRecord
     * @return
     */
    int linkLikeAction(LinkMessageLikesRecord linkMessageLikesRecord);

    /**
     * 获得我点赞的友链留言
     * @param userId
     * @return
     */
    List<String> getOwnLinkLikeId(long userId);
}
