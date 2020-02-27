package com.pzk.community.controller;

import com.pzk.community.dto.NotificationDto;
import com.pzk.community.dto.PaginationDto;
import com.pzk.community.mapper.NotificationMapper;
import com.pzk.community.model.User;
import com.pzk.community.service.NotificationService;
import com.pzk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationMapper notificationMapper;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "5") Integer size) {


        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
            PaginationDto myPageInationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("paginationDto", myPageInationDTO);
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            //通知
            List<NotificationDto> notificationDtoList = notificationService.getNotification(user.getId());
            model.addAttribute("notificationDtoList", notificationDtoList);
        }

        return "profile";
    }
}
