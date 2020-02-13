package com.pzk.community.mapper;

import com.pzk.community.domain.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
     * 查出所有question，分页查询
     * @return
     * @param offset
     * @param size
     */
    @Select(value="select * from question limit #{offset},#{size}")
    List<Question> findAll(@Param("offset") Integer offset,
                           @Param("size") Integer size);

    /**
     * 查询所有页数
     * @return
     */
    @Select("select count(1) from question")
    Integer findCount();

    /**
     * 查询自己 提出的问题 ，分页查询
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    @Select(value="select * from question where creator=#{userId} limit #{offset},#{size}")
    List<Question> findMyQuestion(@Param("userId") Integer userId,
                                  @Param(value = "offset") Integer offset,
                                  @Param(value = "size") Integer size);

    /**
     * 查询  某个user问题的总页数
     * @param userId
     * @return
     */
    @Select("select count(1) from question where creator=#{userId}")
    Integer findByUserCount(Integer userId);


    @Select("select * from question where id=#{id}")
    Question getId(Integer id);
}
