package com.pzk.community.service;

import com.pzk.community.mapper.UserMapper;
import com.pzk.community.model.User;
import com.pzk.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void saveOrCreate(User user) {
        //根据 AccountId找user 判断是否为空
        UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> dbUser = userMapper.selectByExample(example);
        if(dbUser.size() != 0){
            //不为空，更新操作,gmt_create不需要更新  token也不更新
            User updateUser = new User();
            updateUser.setBio(user.getBio());
            updateUser.setName(user.getName());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setGmtModified(user.getGmtModified());
            updateUser.setToken(user.getToken());

            //根据从数据库找出的dbuser 的id更新
            UserExample example1 = new UserExample();
            example1.createCriteria()
                    .andIdEqualTo(dbUser.get(0).getId());
            userMapper.updateByExampleSelective(updateUser, example1);
        }else{
            //为空 插入操作
            userMapper.insert(user);
        }
    }
}
