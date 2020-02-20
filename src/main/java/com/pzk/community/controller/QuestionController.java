package com.pzk.community.controller;

import com.pzk.community.dto.CommentUserDto;
import com.pzk.community.dto.QuestionDto;
import com.pzk.community.service.CommentService;
import com.pzk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model){

        QuestionDto questionDto = questionService.getById(id);

        List<CommentUserDto> commentUserDtoList = commentService.listByQuestionId(id);
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("commentUserDtoList",commentUserDtoList);
        return "question";
    }
}
