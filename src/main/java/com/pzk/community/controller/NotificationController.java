package com.pzk.community.controller;

import com.pzk.community.dto.NotificationDto;
import com.pzk.community.enums.NotificationTypeEnum;
import com.pzk.community.model.Notification;
import com.pzk.community.model.User;
import com.pzk.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String read(@PathVariable("id")Long id,
                       HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }

        NotificationDto notificationDto = notificationService.read(id,user);

        if (NotificationTypeEnum.REPLY_QUESTION.getType() == 1 ||
                NotificationTypeEnum.REPLY_COMMENT.getType() == 2){

            return "redirect:/question/"+notificationDto.getQuestionId();
        }else {
            return "/";
        }
    }
}
