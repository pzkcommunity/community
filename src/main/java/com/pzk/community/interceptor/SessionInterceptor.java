package com.pzk.community.interceptor;

import com.pzk.community.mapper.UserMapper;
import com.pzk.community.model.User;
import com.pzk.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 自定义拦截器
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //取出cookie 并去数据库中查找 user
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("token".equals(name)){
                    String token = cookie.getValue();
                    UserExample example = new UserExample();
                    example.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> user = userMapper.selectByExample(example);
                    if(user != null &&user.size() != 0){
                        //为了让前端判断 显示登录 还是 我
                        request.getSession().setAttribute("user",user.get(0));
                    }
                    break;
                }
            }
        }

        //拦截放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
