package com.neroarc.blog.myblog.control;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "playList",key = "#id")
    @RequestMapping("/music/getPlayList")
    public Object getLikePlayList(String id){
        String url = "http://music.163.com/api/playlist/detail?id="+id;
        RestTemplate restTemplate = new RestTemplate();

        //注意使用String.class
        String body = restTemplate.getForEntity(url, String.class).getBody();
        //总的数据
        JSONObject jsonObject = (JSONObject) JSONObject.fromObject(body).get("result");
        JSONObject itemJson = new JSONObject();
        //包含歌曲id等信息
        JSONArray tracksJsonArray = (JSONArray)jsonObject.get("tracks");
        JSONArray returnJsonArray = new JSONArray();
        //存放歌手信息
        StringBuilder artistBuffer = new StringBuilder();

        for (Object object : tracksJsonArray) {

            JSONObject tempJson = (JSONObject)object;
            JSONArray artists = (JSONArray) tempJson.get("artists");
            JSONObject albumJson  = (JSONObject) tempJson.get("album");

            itemJson.put("name", tempJson.get("name"));
            itemJson.put("song_id", tempJson.get("id"));
            itemJson.put("cover", albumJson.get("picUrl"));


            for (Object artist : artists) {
                JSONObject artistTemJson = (JSONObject) artist;
                artistBuffer.append(artistTemJson.get("name")+"/");
            }

            int lastArtist = artistBuffer.lastIndexOf("/");
            itemJson.put("author", artistBuffer.toString().substring(0, lastArtist));

            //清空旧字符,注意+1
            artistBuffer.delete(0, lastArtist+1);
            returnJsonArray.add(itemJson);

        }
        return returnJsonArray;
    }



    @RequestMapping("/music/getMusicUrl")
    public Object getMusicDetail(String id) {
        String url = "http://music.163.com/api/song/enhance/player/url?ids=["+id+"]&br=320000";
        RestTemplate restTemplate = new RestTemplate();
        String body = restTemplate.getForEntity(url, String.class).getBody();
        JSONArray resultJsonArray = (JSONArray) (JSONObject.fromObject(body).get("data"));

        //返回字符串是返回的数据中存在null,直接返回对象会报空指针错误
        return resultJsonArray.get(0).toString();

    }


}
