package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.service.AdminService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fjx
 * @date: 2019/5/16 20:16
 * Descripe:
 */
@RestController
public class AdminControl {


    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/getNotReadingComment")
    public JSONObject getNotReadingComment(int rows, int pageNum){
        return adminService.getNotReadingComment(rows, pageNum);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/getNotReadingLeaveMessage")
    public JSONObject getNotReadingLeaveMessage(int rows,int pageNum){
        return adminService.getNotReadingLeaveMessage(rows,pageNum);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/getNotReadingCommentCount")
    public JSONObject getNotReadingCommentCount(){
        return adminService.getNotReadingCommentCount();
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/getNotReadingLeaveMessageCount")
    public JSONObject getNotReadingLeaveMessageCount(){
        return adminService.getNotReadingLeaveMessageCount();
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/updateCommentRead")
    public JSONObject updateCommentRead(long commentId){
        int flag = adminService.updateCommentRead(commentId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",500);
        if(flag==1){
            jsonObject.put("status",200);
        }

        return jsonObject;
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/updateMessageRead")
    public JSONObject updateMessageRead(long messageId){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",500);
        int flag = adminService.updateMessageRead(messageId);
        if(flag==1){
            jsonObject.put("status",200);
        }

        return jsonObject;
    }


    @RequestMapping("/getAllCounts")
    public JSONObject getAllCounts(){
        return adminService.getAllCounts();
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/clearAllComment")
    public JSONObject clearAllComment(){
        int flag = adminService.clearAllComment();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",500);
        if(flag!=0){
            jsonObject.put("status",200);
        }

        return jsonObject;
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/clearAllMessage")
    public JSONObject clearAllMessage(){
        JSONObject jsonObject = new JSONObject();
        int flag = adminService.clearAllMessage();
        jsonObject.put("status",500);
        if(flag!=0){
            jsonObject.put("status",200);
        }

        return jsonObject;
    }


}
