package com.neroarc.blog.myblog;

import com.neroarc.blog.myblog.mapper.CommentMapper;
import com.neroarc.blog.myblog.mapper.TagsMapper;
import com.neroarc.blog.myblog.service.ArchiveService;
import com.neroarc.blog.myblog.service.ArticleService;
import com.neroarc.blog.myblog.service.CommentLikesRecordService;
import com.neroarc.blog.myblog.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.context.junit4.SpringRunner;

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

	@Test
	public void contextLoads() {
		//String test = "<h3 id=\"h3-u6D4Bu8BD5u7B2Cu4E09u6B21\"><a name=\"测试第三次\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>测试第三次</h3>";
		//System.out.println(articleService.getSummary(test));
		//userService.getTelephoneCode("18227805311");
		//commentLikesRecordService.getOwnCommentLikeId(1,1);
//		archiveService.getArchiveArticlesByTime("2019-04-16",1,4);
//		archiveService.getArchiveDetails();
//		List<String> parents = commentMapper.getParentReplyCommentsId(2);
//		System.out.println(parents.size());
		List<Map<String,String>> list = new ArrayList<>();
		list = tagsMapper.getTagsCloudPlus();
		for(Map<String,String> map:list){
			Object object = map.get("num");
//			map.get("num").toString();
			if(object instanceof Long){
				System.out.println("long called");
			}

			String str  = String.valueOf(map.get("num"));
			System.out.println(str);
			System.out.println(map.get("tag"));
		}
		System.out.println(list);
	}

}

