package cn.com.demo.module.file;

import cn.com.demo.module.file.common.FileMessageCode;
import cn.com.demo.common.exception.AppException;
import cn.com.demo.module.file.storage.upyun.UpYun;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by demo
 */
public class UpyunUploader implements IFileUploader {
    private String bucket;
    private String operatorName;
    private String operatorPassword;

    public UpyunUploader(String bucket, String operatorName, String operatorPassword){
        this.bucket = bucket;
        this.operatorName = operatorName;
        this.operatorPassword = operatorPassword;
    }

    @Override
    public String upload(File file, String uploadFileName) throws AppException {
        UpYun upyun = new UpYun(bucket, operatorName, operatorPassword);
        upyun.setDebug(true);

        final String path = file.getAbsolutePath();

        Map<String, String> params = new HashMap<>();
        boolean success;
        try {
            success = upyun.writeFile(path, file, true, params);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(FileMessageCode.FILE_UPLOAD_ERROR);
        }
        if(!success){
            throw new AppException(FileMessageCode.FILE_UPLOAD_ERROR);
        }
        file.delete();
        return path;
    }
}
