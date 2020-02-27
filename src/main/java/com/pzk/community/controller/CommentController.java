package com.pzk.community.controller;

import com.pzk.community.dto.CommentDto;
import com.pzk.community.dto.CommentUserDto;
import com.pzk.community.dto.ResultDto;
import com.pzk.community.enums.CommentTypeEnum;
import com.pzk.community.exception.CustomizeErrorCode;
import com.pzk.community.model.Comment;
import com.pzk.community.model.User;
import com.pzk.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody CommentDto commentDto,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
//            ResultDto.errorOf(CustomizeErrorCode.NOT_LOGIN);
            return ResultDto.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if (commentDto == null || StringUtils.isBlank(commentDto.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_NOT_EMPTY);
        }
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        comment.setType(commentDto.getType());
        comment.setParentId(commentDto.getParentId());
        comment.setCommentator(user.getId());


        commentService.insert(comment);

        return ResultDto.okOf();
    }

    /**
     * 二级评论
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDto<List<CommentUserDto>> comment(@PathVariable("id")Long id){

        List<CommentUserDto> commentUserDtoList = commentService.findByParentIdAndType(id, CommentTypeEnum.Comment.getType());


        return ResultDto.okOf(commentUserDtoList);
    }

}
