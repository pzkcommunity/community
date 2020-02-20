package com.pzk.community.commentmapper;

import com.pzk.community.CommunityApplication;
import com.pzk.community.dto.CommentUserDto;
import com.pzk.community.enums.CommentTypeEnum;
import com.pzk.community.mapper.CommentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * springboot集成junit 测试mybatis
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class)
public class commentTest {

    @Autowired
    private CommentMapper commentMapper;
    @Test
    public void test(){
        List<CommentUserDto> commentUserDtos = commentMapper.selectByParentIdAndType(10l, CommentTypeEnum.Question.getType());
        for (CommentUserDto commentUserDto : commentUserDtos) {
            System.out.println(commentUserDto);
        }
    }
}
