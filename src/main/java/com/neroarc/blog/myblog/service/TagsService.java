package com.neroarc.blog.myblog.service;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
/**
 * @author: fjx
 * @date: 2019/3/5 9:57
 * Descripe:
 */

@Service
public interface TagsService {

    /**
     * 根据标签获得同标签的文章
     * @param tag
     * @param rows
     * @param pageNum
     * @return
     */
    JSONObject getArticlesByTag(String tag,int rows, int pageNum);

    /**
     * 获得标签云(旧,通过java来去重)
     * @return
     */
    JSONObject getTagsCloud();

    /**
     * 获得标签云(新,在mysql中实现去重)
     * @return
     */
    JSONObject getTagsCloudPlus();
}
