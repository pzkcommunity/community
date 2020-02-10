package com.pzk.community.mapper;

import com.pzk.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IUserMapper {

    @Select(value={"select * from user"})
    List<User> findAll();

    @Insert("insert into user values(null,#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void save(User user);
}
