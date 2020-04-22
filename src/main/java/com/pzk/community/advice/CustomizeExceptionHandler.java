package com.pzk.community.advice;

import com.alibaba.fastjson.JSON;
import com.pzk.community.dto.ResultDto;
import com.pzk.community.exception.CustomizeErrorCode;
import com.pzk.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(CustomizeException.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回json
            ResultDto resultDto = null;
            if (e instanceof CustomizeException) {
                resultDto = ResultDto.errorOf((CustomizeException) e);
            } else {
                resultDto = ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                //将 resultDto对象序列化成json 格式 输出到页面
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException e1) {
                //测试版本
            }
            return null;
        } else {
            //错误页面
            if (e instanceof CustomizeException) {
                String message = e.getMessage();
                model.addAttribute("message", message);
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR);
            }
            return new ModelAndView("error");
        }
    }
}

