package cn.com.demo.module.file;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.module.file.common.FileMessageCode;
import cn.com.demo.module.file.storage.aliyun.AliyunOss;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created by demo
 */
public class AliyunOssUploader implements IFileUploader {

    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

    private String accessKeyId;

    private String accessKeySecret;

    private String bucket;

    public AliyunOssUploader(String accessKeyId, String accessKeySecret,String bucket) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucket = bucket;
    }

    private AliyunOssUploader() {
    }

    @Override
    public String upload(File file, String uploadFileName) throws AppException {
        AliyunOss aliyunOss = new AliyunOss(endpoint, accessKeyId, accessKeySecret, bucket);
        if (!file.exists()) {
            throw new AppException(FileMessageCode.FILE_NOT_EXIST_ERROR, file.getName());
        }
        String localFilePath = file.getAbsolutePath();
        if (StringUtils.isBlank(uploadFileName)) {
            uploadFileName = file.getName();
        }
        boolean success = aliyunOss.upload(uploadFileName, new File(localFilePath));
        if (!success) {
            throw new AppException(FileMessageCode.FILE_UPLOAD_ERROR);
        }
        return uploadFileName;
    }
}
