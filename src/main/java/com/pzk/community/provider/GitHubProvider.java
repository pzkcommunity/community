package com.pzk.community.provider;

import com.alibaba.fastjson.JSON;
import com.pzk.community.dto.AccessTokenDto;
import com.pzk.community.dto.GitHubUser;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 */
@Component
public class GitHubProvider {

    /**
     * 获取access_token方法
     * @param accessTokenDto
     * @return
     */
    public String getAccessToken(AccessTokenDto accessTokenDto) {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            //System.out.println(s);//access_token=e85f491e6a240eadb0aee66f14309fb749774245&scope=user&token_type=bearer
            //切分获取access_token
            String[] split = s.split("&");
            String access_token = split[0].split("=")[1];
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            //把 string 的json对象转化为 githubuser类对象
            GitHubUser gitHubUser = JSON.parseObject(s, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
