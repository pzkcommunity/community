package com.pzk.community.controller;

import com.github.pagehelper.PageInfo;
import com.pzk.community.dto.QuestionDto;
import com.pzk.community.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public String search(@RequestParam("search") String search,
                         @RequestParam(value = "page",defaultValue ="1")int pageNum,
                         @RequestParam(value = "pageSize",defaultValue = "5")int pageSize,
                         Model model){

        PageInfo<QuestionDto> bySearch = searchService.findBySearch(pageNum, pageSize, search);

        model.addAttribute("bySearch",bySearch);
        model.addAttribute("search",search);
        return "search";
    }
}
