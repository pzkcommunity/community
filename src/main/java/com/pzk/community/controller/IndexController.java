package com.pzk.community.controller;

import com.pzk.community.dto.QuestionDto;
import com.pzk.community.mapper.IQuestionMapper;
import com.pzk.community.mapper.IUserMapper;
import com.pzk.community.model.Question;
import com.pzk.community.model.User;
import com.pzk.community.service.QuestionService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IUserMapper iUserMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){
        //取出cookie 并去数据库中查找 user
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("token".equals(name)){
                    String token = cookie.getValue();
                    User user = iUserMapper.findByToken(token);
                    if(user != null){
                        //为了让前端判断 显示登录 还是 我
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        List<QuestionDto> questionList = questionService.list();

        model.addAttribute("questions",questionList);


        return "index";
    }
}
