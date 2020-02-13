package com.pzk.community.service;

import com.pzk.community.dto.PaginationDto;
import com.pzk.community.dto.QuestionDto;
import com.pzk.community.mapper.IQuestionMapper;
import com.pzk.community.mapper.IUserMapper;
import com.pzk.community.domain.Question;
import com.pzk.community.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * question业务层
 */
@Service
public class QuestionService {

    @Autowired
    private IQuestionMapper iQuestionMapper;

    @Autowired
    private IUserMapper iUserMapper;


    /**
     * 查出所有的问题 以及 分页
     * @return
     * @param page
     * @param size
     */
    public PaginationDto list(Integer page, Integer size) {

        //每页从那条数据开始  (i-1)*5
        Integer offset = (page-1)*size;

        List<QuestionDto> list = new ArrayList<>();

        PaginationDto paginationDto = new PaginationDto();
        List<Question> questions = iQuestionMapper.findAll(offset,size);
            for (Question question : questions) {
                User user = iUserMapper.findById(question.getCreator());
                QuestionDto questionDto = new QuestionDto();
                //把question属性值 赋值给questionDto 的属性
                BeanUtils.copyProperties(question,questionDto);
                questionDto.setUser(user);
                list.add(questionDto);
            }
            //查询所有页数
            Integer count = iQuestionMapper.findCount();

            paginationDto.setQuestionDtoList(list);
            //page是传递过来的第几页 count是总页数 size是每页几条数据
            paginationDto.setPageInation(count,page,size);
        return paginationDto;
    }

    /**
     * 查 自己提出的问题 以及封装的 分页
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDto list(Integer userId, Integer page, Integer size) {

        //每页从那条数据开始  (i-1)*5
        Integer offset = (page-1)*size;

        List<QuestionDto> list = new ArrayList<>();

        PaginationDto paginationDto = new PaginationDto();
        List<Question> questions = iQuestionMapper.findMyQuestion(userId,offset,size);
        for (Question question : questions) {
            User user = iUserMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            //把question属性值 赋值给questionDto 的属性
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            list.add(questionDto);
        }
        //查询所有页数
        Integer count = iQuestionMapper.findByUserCount(userId);

        paginationDto.setQuestionDtoList(list);
        //page是传递过来的第几页 count是总页数 size是每页几条数据
        paginationDto.setPageInation(count,page,size);
        return paginationDto;
    }

    public QuestionDto getId(Integer id) {
        
        Question question = iQuestionMapper.getId(id);
        QuestionDto questionDto = new QuestionDto();

        BeanUtils.copyProperties(question, questionDto);

        User user = iUserMapper.findById(question.getCreator());
        questionDto.setUser(user);
        
        return questionDto;
    }
}
