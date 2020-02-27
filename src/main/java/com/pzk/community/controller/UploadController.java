package com.pzk.community.controller;

import com.pzk.community.dto.UploadDto;
import com.pzk.community.provider.UcloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class UploadController {

    @Autowired
    private UcloudProvider ucloudProvider;

    @ResponseBody
    @RequestMapping("file/upload")
    public UploadDto upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //获取输入框的 name
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        UploadDto uploadDto = new UploadDto();

        try {
            String url = ucloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            uploadDto.setSuccess(1);
            //上传到地址
            uploadDto.setUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadDto;
    }
}
