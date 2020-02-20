package com.pzk.community.service;

import com.pzk.community.dto.PaginationDto;
import com.pzk.community.dto.QuestionDto;
import com.pzk.community.exception.CustomizeErrorCode;
import com.pzk.community.exception.CustomizeException;
import com.pzk.community.mapper.QuestionMapper;
import com.pzk.community.mapper.UserMapper;
import com.pzk.community.model.Question;
import com.pzk.community.model.QuestionExample;
import com.pzk.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


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
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
            for (Question question : questions) {
                User user = userMapper.selectByPrimaryKey(question.getCreator());
                QuestionDto questionDto = new QuestionDto();
                //把question属性值 赋值给questionDto 的属性
                BeanUtils.copyProperties(question,questionDto);
                questionDto.setUser(user);
                list.add(questionDto);
            }
            //查询所有页数
        QuestionExample example = new QuestionExample();
        Integer count = (int)questionMapper.countByExample(example);

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
    public PaginationDto list(Long userId, Integer page, Integer size) {

        //每页从那条数据开始  (i-1)*5
        Integer offset = (page-1)*size;

        List<QuestionDto> list = new ArrayList<>();

        PaginationDto paginationDto = new PaginationDto();
        //根据creator 查询 问题
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            //把question属性值 赋值给questionDto 的属性
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            list.add(questionDto);
        }
        //根据creator查询所有 条数
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria()
                .andCreatorEqualTo(userId);
        Integer count = (int)questionMapper.countByExample(example1);

        paginationDto.setQuestionDtoList(list);
        //page是传递过来的第几页 count是总页数 size是每页几条数据
        paginationDto.setPageInation(count,page,size);
        return paginationDto;
    }

    /**
     * 根据id查出 QuestionDto
     * @param id
     * @return
     */
    public QuestionDto getById(Long id) {
        
        Question question = questionMapper.selectByPrimaryKey(id);
        //如果question 不存在抛出异常
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();

        BeanUtils.copyProperties(question, questionDto);

        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        
        return questionDto;
    }

    //edit 后的question
    public void saveOrUpdate(Question question) {

        //如果 是第一次发布则 id为空，如果是修改 则主键id肯定不为空
        Long id = question.getId();
        //判断是否为空
        if(id == null){
            //保存
            questionMapper.insert(question);
        }else{
            //不更改 初次发布时间
            Question updateQuestion = new Question();
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setGmtModified(question.getGmtModified());
            QuestionExample example = new QuestionExample();
            //根据id更新
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int i = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(i != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 评论数
     * @param id
     */
    public void incView(Long id) {

        //每次都传递 一个 1 表示每次 都+1 viewCount = viewCount+1
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);

        questionMapper.incView(question);
    }
}
