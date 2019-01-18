package cn.com.demo.module.file.storage.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

import java.io.File;

/**
 * Created by demo on 2018/9/7.
 */
public class AliyunOss {
    /**
     * OSSClient实例
     */
    private OSSClient ossClient;
    private String bucket;

    public AliyunOss(String endpoint, String accessKeyId, String accessKeySecret, String bucket) {
        // 创建OSSClient实例。
        this.bucket = bucket;
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    private AliyunOss() {
    }

    /**
     * 文件上传
     *
     * @param fileName 文件名
     * @param file     文件
     */
    public boolean upload(String fileName, File file) {
        PutObjectResult result;
        result = ossClient.putObject(bucket, fileName, file);
        ossClient.shutdown();
        int statusCode = 200;
        if (result != null && result.getResponse() != null) {
            statusCode = result.getResponse().getStatusCode();
        }
        return statusCode == 200;
    }
}
