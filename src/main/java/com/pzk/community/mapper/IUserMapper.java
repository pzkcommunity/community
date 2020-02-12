package com.pzk.community.mapper;

import com.pzk.community.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IUserMapper {

    /**
     * 查找所有user
     * @return
     */
    @Select(value={"select * from user"})
    List<User> findAll();

    /**
     * 保存user数据
     * @param user
     */
    @Insert("insert into user values(null,#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified},#{bio},#{avatar_url})")
    void save(User user);

    /**
     * 根据token找user
     * @param token
     * @return
     */
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    /**
     * 根据id查找user
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
}
