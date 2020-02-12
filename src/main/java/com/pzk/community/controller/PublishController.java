package com.pzk.community.controller;

import com.pzk.community.mapper.IQuestionMapper;
import com.pzk.community.mapper.IUserMapper;
import com.pzk.community.model.Question;
import com.pzk.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 提问
 */
@Controller
public class PublishController {

    @Autowired
    private IQuestionMapper iQuestionMapper;

    @Autowired
    private IUserMapper iUserMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
        ){

        //只能在publish页面可以取到
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);


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


        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("token".equals(name)){
                    String token = cookie.getValue();
                     user = iUserMapper.findByToken(token);
                    System.out.println(user);
                    if(user != null){
                        //为了让前端判断 显示登录 还是 我
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
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
        question.setGmtCreate(user.getGmtCreate());
        question.setGmtModified(user.getGmtModified());
        iQuestionMapper.saveQuestion(question);

        return "redirect:/";
    }
}
