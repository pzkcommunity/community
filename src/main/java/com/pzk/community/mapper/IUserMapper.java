package com.pzk.community.mapper;

import com.pzk.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IUserMapper {

    @Select(value={"select * from user"})
    List<User> findAll();

    @Insert("insert into user values(null,#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified},#{bio},#{avatar_url})")
    void save(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
}
