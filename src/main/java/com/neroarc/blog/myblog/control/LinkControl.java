package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.model.Link;
import com.neroarc.blog.myblog.service.LinkService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fjx
 * @date: 2019/6/19 21:48
 * Descripe:
 */
@RestController
public class LinkControl {

    @Autowired
    private LinkService linkService;

    @RequestMapping("/getLink")
    public JSONObject getLink(int id){
        return linkService.getLink(id);
    }

    @RequestMapping("/addLink")
    public JSONObject addLink(Link link){
        JSONObject jsonObject = new JSONObject();
        int flag = linkService.addLink(link);

        jsonObject.put("status",500);
        if(flag!=0){
            jsonObject.put("status",200);
        }

        return jsonObject;

    }

    @RequestMapping("/updateLink")
    public JSONObject updateLink(Link link){
        JSONObject jsonObject = new JSONObject();
        int flag = linkService.updateLink(link);

        jsonObject.put("status",500);
        if(flag!=0){
            jsonObject.put("status",200);
        }

        return jsonObject;
    }

    @RequestMapping("/deleteLink")
    public JSONObject deleteLink(int id){
        JSONObject jsonObject = new JSONObject();
        int flag = linkService.deleteLink(id);

        jsonObject.put("status",500);
        if(flag!=0){
            jsonObject.put("status",200);
        }

        return jsonObject;
    }

    @RequestMapping("/getLinks")
    public JSONObject getLinks(int rows,int pageNum){
        return linkService.getLinks(rows,pageNum);
    }

    @RequestMapping("/getAllLinks")
    public JSONObject getAllLinks(){
        return linkService.getAllLinks();
    }
}
