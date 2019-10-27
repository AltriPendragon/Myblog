package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.model.Image;
import com.neroarc.blog.myblog.service.ArticleService;
import com.neroarc.blog.myblog.service.ImageService;
import com.neroarc.blog.myblog.utils.DateUtil;
import com.neroarc.blog.myblog.utils.FileUtil;
import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: fjx
 * @date: 2019/3/6 21:17
 * Descripe:
 */

@RestController
public class ImageControl {

    @Autowired
    private ImageService imageService;

    @Autowired
    ArticleService articleService;


    @RequestMapping("/uploadHeadImage")
    public JSONObject uploadHeadImage(HttpServletRequest request, @AuthenticationPrincipal Principal principal){

        String img = request.getParameter("img");
        int index = img.indexOf(";base64,");
        String strFileExtendName = "." + img.substring(11,index);
        img = img.substring(index + 8);
        String filePath = this.getClass().getResource("/").getPath().substring(1) + "userImg/";
        FileUtil fileUtil = new FileUtil();
        String time = Long.toString(DateUtil.getLongTime());
        File file = fileUtil.base64ToFile(filePath,img,time+strFileExtendName);

        String returnStr = imageService.uploadImage(file,principal.getName()+"/headImg");

        JSONObject jsonObject = new JSONObject();
        if(returnStr==null){
            jsonObject.put("status",400);
        }
        jsonObject.put("status",200);
        jsonObject.put("url",returnStr);
        return jsonObject;
    }

    @RequestMapping("/uploadBlogImg")
    public JSONObject uploadBlogImg(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, @AuthenticationPrincipal Principal principal, HttpServletResponse response){
        FileUtil fileUtil = new FileUtil();
        JSONObject jsonObject = new JSONObject();

        //X-Frame-Options 主管iframe是否可以加载内容。一般默认为 deny。需要调整为可以加载同源域名下得页面
        //也可以设置在security里面，http.headers().frameOptions().sameOrigin();
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        //User user = userService.getUserByProviderId(principal.getName());

        String filePath = this.getClass().getResource("/").getPath().substring(1) + "blogImg/";
        String fileContentType = file.getContentType();
        String fileExtension = fileContentType.substring(fileContentType.indexOf("/") + 1);
        String fileName = DateUtil.getLongTime() + "." + fileExtension;

        String returnStr = imageService.uploadImage(fileUtil.multipartFileToFile(file,filePath,fileName),"1"+"/blogImg");

        jsonObject.put("url",returnStr);
        jsonObject.put("success",1);
        jsonObject.put("message","upload success");
        return jsonObject;


    }


    @RequestMapping("/addImage")
    public Map<String,Object> addBgImage(Image image){
        int flag = imageService.addBgImage(image);
        Map<String,Object> statusMap = new HashMap<>(1);
        if (flag==0){
            statusMap.put("status", 500);
            return statusMap;
        }

        statusMap.put("status", 200);
        return statusMap;
    }

    @RequestMapping("/updateImage")
    public Map<String, Object> updateBgImage(Image image) {
        int flag = imageService.updateBgImage(image);
        Map<String, Object> statusMap = new HashMap<>(1);
        if (flag == 0) {
            statusMap.put("status", 500);
            return statusMap;
        }

        statusMap.put("status", 200);
        return statusMap;
    }


   @RequestMapping("/getBgAllImages")
   @CrossOrigin("http://localhost:63342")
    List<Image> getBgAllImages(){
        return imageService.getBgAllImages();
    }


    @RequestMapping("/getBgImagesByType")
    @CrossOrigin("http://localhost:63342")
    List<Image> getBgImagesByType(int type){
        return imageService.getBgImagesByType(type);
    }

    @RequestMapping("/getBgImageById")
    JSONObject getBgImageById(int id){
       Image image = imageService.getBgImageById(id);
        JSONObject returnJson = new JSONObject();
        if (image == null) {
            returnJson.put("status", 404);
            return returnJson;
        }

        returnJson = JSONObject.fromObject(image);
        returnJson.put("status", 200);
        return returnJson;
    }


    @RequestMapping("/getBgImageByTag")
    List<Image> getBgImageByTag(String tag){
        return imageService.getBgImageByTag(tag);
    }

    @PostMapping("/getPageArticlest")
    public JSONObject getArticles(@RequestBody Map<String,Integer> map){
        return articleService.getPageArticles(map.get("rows"),1);
    }

    @RequestMapping("/searchImageByEs")
    @CrossOrigin("http://localhost:63342")
    public List<Image> searchImageByEs(String search){
        return imageService.searchImageByEs(search);
    }

    @RequestMapping("/getPageBgImages")
    public Map<String, Object> getPageBgImages(int rows, int pageNum) {
        return imageService.getPageBgImage(rows, pageNum);
    }
}
