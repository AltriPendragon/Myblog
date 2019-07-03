package com.neroarc.blog.myblog.control;
import com.neroarc.blog.myblog.service.CategoryService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fjx
 * @date: 2019/3/6 15:30
 * Descripe:
 */
@RestController
public class CategoryControl {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/getArticlesByCategory")
    public JSONObject getArticlesByCategory(String category,int rows,int pageNum){
        return categoryService.getArticlesByCategory(category,rows,pageNum);
    }

    @RequestMapping("/getCategoryDetail")
    public JSONObject getCategoryDetail(){
        return categoryService.getCategoryDetail();
    }
}
