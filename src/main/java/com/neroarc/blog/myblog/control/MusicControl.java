package com.neroarc.blog.myblog.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: fjx
 * @date: 2019/6/3 10:44
 * Descripe:
 */

@RestController
public class MusicControl {

    @RequestMapping("/music/getPlayList")
    public Object getPlayList(String id){
        String url = "http://120.79.36.48/playlist/detail?id="+id;
        RestTemplate restTemplate = new RestTemplate();
        Object playList = restTemplate.getForEntity(url,Object.class).getBody();
        return playList;
    }

    @RequestMapping("/music/getMusicUrl")
    public Object getMusicUrl(String id){
        String url = "http://120.79.36.48/music/url?id="+id;
        RestTemplate restTemplate = new RestTemplate();
        Object urlList = restTemplate.getForEntity(url,Object.class).getBody();
        return urlList;
    }


}
