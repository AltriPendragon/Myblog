package com.neroarc.blog.myblog;

import com.neroarc.blog.myblog.mapper.CommentMapper;
import com.neroarc.blog.myblog.mapper.MusicMapper;
import com.neroarc.blog.myblog.mapper.TagsMapper;
import com.neroarc.blog.myblog.service.ArchiveService;
import com.neroarc.blog.myblog.service.ArticleService;
import com.neroarc.blog.myblog.service.CommentLikesRecordService;
import com.neroarc.blog.myblog.service.UserService;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyblogApplicationTests {

	@Autowired
	ArticleService articleService;

	@Autowired
	UserService userService;

	@Autowired
	CommentLikesRecordService commentLikesRecordService;

	@Autowired
	ArchiveService archiveService;

	@Autowired
	CommentMapper commentMapper;

	@Autowired
	TagsMapper tagsMapper;


	@Autowired
	MusicMapper musicMapper;




	@Test
	public void contextLoads() {

//		String url = "https://api.i-meto.com/meting/api?server=netease&type=playlist&id=819935406";
//		RestTemplate restTemplate = new RestTemplate();
//		String body = restTemplate.getForEntity(url, String.class).getBody();
//		JSONArray jsonArray = JSONArray.fromObject(body);
//		musicMapper.pullAllMusic(jsonArray);


	}

}

