package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.service.ArchiveService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fjx
 * @date: 2019/3/6 19:39
 * Descripe:
 */

@RestController
public class ArchiveControl {

    @Autowired
    private ArchiveService archiveService;

    @RequestMapping("/getArchiveArticlesByTime")
    public JSONObject getArchiveArticlesByTime(String time,int rows,int pageNum){
        return archiveService.getArchiveArticlesByTime(time,rows,pageNum);
    }

    @RequestMapping("/getArchiveDetails")
    public JSONObject getArchiveDetails(){
        return archiveService.getArchiveDetails();
    }

    @RequestMapping("/getArchiveArticles")
    public JSONObject getArchiveArticles(int rows,int pageNum){
        return archiveService.getArchiveArticles(rows,pageNum);
    }
}
