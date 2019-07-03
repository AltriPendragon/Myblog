package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.service.ImageService;
import com.neroarc.blog.myblog.utils.DateUtil;
import com.neroarc.blog.myblog.utils.FileUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.security.Principal;

/**
 * @author: fjx
 * @date: 2019/3/6 21:17
 * Descripe:
 */

@RestController
public class ImageControl {

    @Autowired
    private ImageService imageService;


    @RequestMapping("/uploadHeadImage")
    public JSONObject uploadHeadImage(HttpServletRequest request, @AuthenticationPrincipal Principal principal){

        String img = request.getParameter("img");
        int index = img.indexOf(";base64,");
        String strFileExtendName = "." + img.substring(11,index);
        img = img.substring(index + 8);
        String filePath = this.getClass().getResource("/").getPath().substring(1) + "userImg/";
        FileUtil fileUtil = new FileUtil();
        DateUtil dateUtil = new DateUtil();
        String time = Long.toString(dateUtil.getLongTime());
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
}
