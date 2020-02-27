package com.pzk.community.cache;

import com.pzk.community.dto.TagDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    /**
     * 获取 标签库
     * @return
     */
    public static List<TagDto> get(){
        TagDto language = new TagDto();
        language.setCategoryName("开发语言");
        language.setTags(Arrays.asList("javascript","php","java","css","html","html5","node.js","python","c","c++","scala"));

        TagDto framework = new TagDto();
        framework.setCategoryName("框架");
        framework.setTags(Arrays.asList("spring","spring mvc","mybatis","hibernate","struts","express"));

        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("nginx","docker","linux","tomcat","unix","hadoop"));

        TagDto db = new TagDto();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","sqlserver","db2","h2","oracle","mongodb","redis"));

        List<TagDto> tagDtoList = new ArrayList<>();
        tagDtoList.add(language);
        tagDtoList.add(framework);
        tagDtoList.add(server);
        tagDtoList.add(db);
        return tagDtoList;
    }

    /**
     * 过滤掉非法标签
     * @param tags
     * @return
     */
    public static String filterInvalid(String tags){
        //
        String[] split = tags.split(",");
        ///获取标签库 的标签
        List<TagDto> tagDtoList = get();
        //标签库 的标签压平 取出 (categotyName,List<tags>)->List<tags>
        List<String> tagList = tagDtoList.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        //把输入的标签 过滤出不合法的标签
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));


        return invalid;
    }
}
