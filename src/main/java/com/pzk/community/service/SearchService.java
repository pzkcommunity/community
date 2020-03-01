package com.pzk.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pzk.community.dto.QuestionDto;
import com.pzk.community.mapper.QuestionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private QuestionMapper questionMapper;

    public PageInfo<QuestionDto> findBySearch(int pageNum, int pageSize, String search){
        if (StringUtils.isNotBlank(search)){
            String s = search.replaceAll(" +", "|");
            PageHelper.startPage(pageNum,pageSize);
            List<QuestionDto> searchList = questionMapper.findBySearch(s);
            PageInfo<QuestionDto> searchPageInfo = new PageInfo<>(searchList);
            return searchPageInfo;
        } else {
            return null;
        }
    }
}
