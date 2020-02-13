package com.pzk.community.controller;

import com.pzk.community.dto.AccessTokenDto;
import com.pzk.community.dto.GitHubUser;
import com.pzk.community.mapper.IUserMapper;
import com.pzk.community.domain.User;
import com.pzk.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 获取返回的code和state
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;
    @Value(value="${github.client.id}")
    private String clientId;

    @Value(value="${github.client.secret}")
    private String clientSecret;

    @Value(value="${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private IUserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletResponse response){

        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDto);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
//        System.out.println(gitHubUser.getName());
        //判断是否登陆成功
        if(gitHubUser != null){
            //登陆成功保存到数据库
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setBio(gitHubUser.getBio());
            user.setAvatarUrl(gitHubUser.getAvatar_url());
            userMapper.save(user);

            response.addCookie(new Cookie("token",token));
            //登陆成功 重定向到首页 url路径
            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/";
        }
    }
}
