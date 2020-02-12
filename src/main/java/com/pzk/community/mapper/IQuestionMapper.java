package com.pzk.community.mapper;

import com.pzk.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IQuestionMapper {

    /**
     * 保存question
     * @param question
     */
    @Insert("insert into question values(null,#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void saveQuestion(Question question);

    /**
     * 根据creator找question
     * @param creator
     * @return
     */
    @Select("select * from question where creator=#{creator}")
    List<Question> findByCreator(Integer creator);

    /**
     * 查出所有question
     * @return
     */
    @Select(value="select * from question")
    List<Question> findAll();
}
