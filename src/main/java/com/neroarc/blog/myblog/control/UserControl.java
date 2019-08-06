package com.neroarc.blog.myblog.control;

import com.neroarc.blog.myblog.model.User;
import com.neroarc.blog.myblog.service.ImageService;
import com.neroarc.blog.myblog.service.UserService;
import com.neroarc.blog.myblog.utils.DateUtil;
import com.neroarc.blog.myblog.utils.FileUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;

/**
 * @author: fjx
 * @date: 2019/4/19 20:15
 * Descripe:
 */

@RestController
public class UserControl {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageControl imageControl;



    @RequestMapping("/getUserByProviderId")
    public JSONObject getUserByProviderId(String telephone){
        JSONObject jsonObject = new JSONObject();
        User user = userService.getUserByProviderId(telephone);
        if(user==null){
            jsonObject.put("status",200);
            return jsonObject;
        }

        jsonObject.put("status",500);
        return jsonObject;
    }

    @PostMapping("/getTelephoneCode")
    public JSONObject getTelephoneCode(String telephone){
        JSONObject jsonObject = new JSONObject();
        int flag = userService.getTelephoneCode(telephone);
        if(flag!=1){
            jsonObject.put("status",404);
            return jsonObject;
        }

        jsonObject.put("status",200);
        return jsonObject;
    }


    @PostMapping("/register")
    public JSONObject register(User user, String telephoneCode){
        JSONObject jsonObject = new JSONObject();
        int flag = userService.register(user,telephoneCode);

        if(flag!=1){
            jsonObject.put("status",404);
            return jsonObject;
        }

        jsonObject.put("status",200);

        return jsonObject;
    }

    @RequestMapping("/isLogin")
    public JSONObject isLogin(@AuthenticationPrincipal Principal principal){
        JSONObject jsonObject = new JSONObject();
        if(principal==null){
            jsonObject.put("state",0);
            return jsonObject;
        }

        jsonObject.put("state",1);
        jsonObject.put("headImg",userService.getHeadImg(Long.parseLong(principal.getName())));
        jsonObject.put("id",principal.getName());
        return jsonObject;
    }

    @RequestMapping("/isLoginPlus")
    public JSONObject isLoginPlus(@AuthenticationPrincipal Principal principal){
        JSONObject jsonObject = new JSONObject();
        if(principal==null){
            jsonObject.put("state",0);
            return jsonObject;
        }
        jsonObject.put("state",1);
        jsonObject.put("name",userService.getUserById(Long.parseLong(principal.getName())).getName());
        return jsonObject;
    }

    @PostMapping("/updateUserInfo")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public JSONObject updateUserInfo(User user, @AuthenticationPrincipal Principal principal, HttpServletRequest request){

        JSONObject returnJson = new JSONObject();
        returnJson.put("status",500);

        if(request.getParameter("img").contains("base64")){
            JSONObject returnImgJson = imageControl.uploadHeadImage(request,principal);
            if((int)returnImgJson.get("status")==200){
                user.setImageUrl((String)returnImgJson.get("url"));
                user.setId(Long.parseLong(principal.getName()));
                int flag = userService.updateUserInfo(user);
                if(flag==1){
                    returnJson.put("status",200);
                }
            }
        }

        else {
            user.setImageUrl(request.getParameter("img"));
            user.setId(Long.parseLong(principal.getName()));
            int flag = userService.updateUserInfo(user);
            if(flag==1){
                returnJson.put("status",200);
            }
        }

        return returnJson;
    }


    /**
     * 后台获得用户信息
     * @param principal
     * @return
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping("/getUserInfo")
    public JSONObject getUserInfo(@AuthenticationPrincipal Principal principal){
        User user = userService.getUserById(Long.parseLong(principal.getName()));
        JSONObject jsonObject = new JSONObject();
        if(user!=null){
            jsonObject.put("imgUrl",user.getImageUrl());
            jsonObject.put("name",user.getName());
            jsonObject.put("telephone",user.getProviderId());
            jsonObject.put("gender",user.getGender());
            jsonObject.put("info",user.getInfo());
            jsonObject.put("status",200);
            if(user.getProviderId().length()>11){
                jsonObject.put("telephone","");
            }

            if(user.getInfo()==null){
                jsonObject.put("info","");
            }

            return jsonObject;

        }

        jsonObject.put("status",404);
        return jsonObject;
    }

    @RequestMapping("/getReplyComments")
    public JSONObject getReplyComments(@AuthenticationPrincipal Principal principal,int rows,int pageNum){
        return userService.getReplyComments(Long.parseLong(principal.getName()),rows,pageNum);
    }

    @RequestMapping("/getReferMineComments")
    public JSONObject getReferMineComments(@AuthenticationPrincipal Principal principal,int rows,int pageNum){
        return userService.getReferMineComments(Long.parseLong(principal.getName()),rows,pageNum);
    }

    @RequestMapping("/isUserExist")
    public JSONObject isUserExist(long telephone){
        JSONObject jsonObject = new JSONObject();
        int flag = userService.isUserExist(telephone);
        jsonObject.put("status",404);
        if(flag==1){
            jsonObject.put("status",200);
        }

        return jsonObject;

    }

    @RequestMapping("/isUserNameExist")
    public JSONObject isUserNameExist(String name,@AuthenticationPrincipal Principal principal){
        JSONObject jsonObject = new JSONObject();
        int flag = userService.isUserNameExist(name);

        if(flag==1){
            String id = Long.toString(userService.getUserByName(name).getId());
            if(id.equals(principal.getName())){
                flag = 0;
            }
        }
        jsonObject.put("status",404);
        //如果不存在则可以更改，否则则不能
        if(flag==0){
            jsonObject.put("status",200);
        }

        return jsonObject;

    }

    @PostMapping("/resetPassword")
    public JSONObject resetPassword(User user,String telephoneCode){
        JSONObject jsonObject = new JSONObject();

        int flag = userService.resetPassword(user,telephoneCode);

        jsonObject.put("status",500);

        if(flag==1){
            jsonObject.put("status",200);
        }



        return jsonObject;
    }

    @PostMapping("/checkUserNameAndPassword")
    public int checkUserNameAndPassword(@RequestParam("telephone") String telephone, @RequestParam("password") String password){

        User user = userService.getUserByProviderId(telephone);
        if(user==null){
            throw new UsernameNotFoundException("没有当前用户");
        }

        else {
            if(user.getPassword().equals(password)){
                return 1;
            }
        }

        return 0;
    }
}
