package com.pzk.community.controller;

import com.pzk.community.cache.TagCache;
import com.pzk.community.mapper.QuestionMapper;
import com.pzk.community.model.Question;
import com.pzk.community.model.User;
import com.pzk.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 提问
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(Model model){
        //标签库
        model.addAttribute("taglib", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Long id,
            HttpServletRequest request,
            Model model
        ){

        //只能在publish页面可以取到
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //标签库
        model.addAttribute("taglib", TagCache.get());


        if(title == null || title ==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == null || description ==""){
            model.addAttribute("error","问题描述不能为空");
            return "publish";
        }
        if(tag == null || tag ==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        //过滤出非法的标签
        String invalidTags = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalidTags)){
            model.addAttribute("error","输入了非法标签"+invalidTags);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");

        //如果user为空 提示登录
        if(user == null){
            model.addAttribute("error","用户未登录");
            //未登录就不存储question信息，直接跳转到 get请求渲染页面
            return "publish";
        }
        //未登录就不存储question信息
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setViewCount(0);
        question.setCommentCount(0);
        question.setLikeCount(0);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setId(id);
        questionService.saveOrUpdate(question);

        return "redirect:/";
    }

    //编辑
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")Long id,
                       Model model){
        Question question = questionMapper.selectByPrimaryKey(id);
        //回显到页面
        model.addAttribute("title",question.getTitle());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("description",question.getDescription());
        //把question的主键 唯一标识 传回
        model.addAttribute("id",question.getId());
        //标签库
        model.addAttribute("taglib", TagCache.get());

        return  "publish";
    }
}
