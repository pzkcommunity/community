package com.pzk.community.service;

import com.pzk.community.dto.QuestionDto;
import com.pzk.community.mapper.IQuestionMapper;
import com.pzk.community.mapper.IUserMapper;
import com.pzk.community.model.Question;
import com.pzk.community.model.User;
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
     * 根据question的creator找出user
     * @return 返回List<QuestionDto>
     */
    public List<QuestionDto> list() {
        List<QuestionDto> list = new ArrayList<>();
        List<Question> questions = iQuestionMapper.findAll();
            for (Question question : questions) {
                User user = iUserMapper.findById(question.getCreator());
                QuestionDto questionDto = new QuestionDto();
                //把question属性值 赋值给questionDto 的属性
                BeanUtils.copyProperties(question,questionDto);
                questionDto.setUser(user);
                list.add(questionDto);
            }
        return list;
    }
}
