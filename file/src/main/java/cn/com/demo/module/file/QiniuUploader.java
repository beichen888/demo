package cn.com.demo.module.file;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.module.file.common.FileMessageCode;
import cn.com.demo.module.file.storage.qiniu.QiNiu;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created by demo
 */
public class QiniuUploader implements IFileUploader {
    private String accessKey;

    private String secretKey;

    private String bucket;

    public QiniuUploader(String accessKey, String secretKey, String bucket) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
    }

    @Override
    public String upload(File file, String uploadFileName) throws AppException {
        QiNiu qiNiu = new QiNiu(accessKey, secretKey, bucket);
        if (!file.exists()) {
            throw new AppException(FileMessageCode.FILE_NOT_EXIST_ERROR, file.getName());
        }
        String localFilePath = file.getAbsolutePath();
        if (StringUtils.isBlank(uploadFileName)) {
            uploadFileName = file.getName();
        }
        boolean success = qiNiu.upload(localFilePath, uploadFileName);
        if (!success) {
            throw new AppException(FileMessageCode.FILE_UPLOAD_ERROR);
        }
        return uploadFileName;
    }
}
