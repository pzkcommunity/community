package com.pzk.community.advice;

import com.pzk.community.exception.CustomizeExceptiion;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable e, Model model) {
        if (e instanceof CustomizeExceptiion) {
            String message = e.getMessage();
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", "服务过于繁忙，请稍后再试试");
        }
        return new ModelAndView("error");
    }
}

