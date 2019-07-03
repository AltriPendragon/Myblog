package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.model.Comment;
import com.neroarc.blog.myblog.model.CommentLikesRecord;
import com.neroarc.blog.myblog.service.CommentLikesRecordService;
import com.neroarc.blog.myblog.service.CommentService;
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
 * @date: 2019/4/24 9:09
 * Descripe:
 */

@RestController
public class CommentControl {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentLikesRecordService commentLikesRecordService;


    @RequestMapping("/addComment")
    public JSONObject addComment(Comment comment, @AuthenticationPrincipal Principal principal){

        comment.setRemarkerId(Integer.parseInt(principal.getName()));
        comment.setCommentDate(DateUtil.getStringTime());

        return commentService.addComment(comment);
    }


    @RequestMapping("/getCommentsOfArticle")
    public JSONObject getCommentsOfArticle(long articleId,@AuthenticationPrincipal Principal principal){
        long userId = -1;
        if(principal!=null){
            userId = Long.parseLong(principal.getName());
        }
        return commentService.getCommentsOfArticle(articleId,userId);
    }


    @RequestMapping("/commentLikeAction")
    public int commentLikeAction(CommentLikesRecord commentLikesRecord, @AuthenticationPrincipal Principal principal){
        commentLikesRecord.setLikerId(Long.parseLong(principal.getName()));
        commentLikesRecord.setDate(DateUtil.getStringTime());
        return commentLikesRecordService.commentLikeAction(commentLikesRecord);
    }

}
