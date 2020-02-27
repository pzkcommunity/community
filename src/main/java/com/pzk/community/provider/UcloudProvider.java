package com.pzk.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.BucketAuthorization;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.pzk.community.exception.CustomizeErrorCode;
import com.pzk.community.exception.CustomizeException;
import com.pzk.community.exception.ICustomizeErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Component
public class UcloudProvider {

    @Value("${ucloud.ufile.public-key}")
    private String publicKey;

    @Value("${ucloud.ufile.private-key}")
    private String privateKey;


    public String upload(InputStream fileStream, String mimeType,String fileName){
        String[] split = fileName.split("\\.");

        if (split.length >1 ){
            fileName = UUID.randomUUID().toString()+"."+split[split.length-1];
        } else {
            return null;
        }

        try {
            // Bucket相关API的授权器
            ObjectAuthorization BUCKET_AUTHORIZER = new UfileObjectLocalAuthorization(
                    publicKey, privateKey);

            // 对象操作需要ObjectConfig来配置您的地区和域名后缀
            ObjectConfig config = new ObjectConfig("cn-bj", "ufileos.com");
            String bucketName = "mosini";
            PutObjectResultBean response = UfileClient.object(BUCKET_AUTHORIZER,config)
                    .putObject(fileStream, mimeType)
                    .nameAs(fileName)
                    .toBucket(bucketName)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();
            //System.out.println(response);// response 的restcode=0
            if (response != null && response.getRetCode() == 0){
                String url = UfileClient.object(BUCKET_AUTHORIZER,config)
                        .getDownloadUrlFromPrivateBucket(fileName,bucketName,24*60*60*365*10)
                        .createUrl();
                return url;
            } else {
                throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }



}
