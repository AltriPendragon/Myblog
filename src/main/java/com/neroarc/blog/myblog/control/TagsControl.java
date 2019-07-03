package com.neroarc.blog.myblog.control;
import com.neroarc.blog.myblog.service.TagsService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fjx
 * @date: 2019/3/5 10:33
 * Descripe:
 */

@RestController
public class TagsControl {

    @Autowired
    private TagsService tagsService;

    @RequestMapping("/getArticlesByTag")
    public JSONObject getArticlesByTag(@RequestParam("tag") String tag, int rows, int pageNum){
        return tagsService.getArticlesByTag(tag,rows,pageNum);
    }


    @RequestMapping("/getTagsCloud")
    public JSONObject getTagsCloud(){
        return tagsService.getTagsCloud();
    }

    @RequestMapping("/getTagsCloudPlus")
    public JSONObject getTagsCloudPlus(){
        return tagsService.getTagsCloudPlus();
    }
}
