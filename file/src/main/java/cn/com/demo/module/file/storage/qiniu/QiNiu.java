package cn.com.demo.module.file.storage.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * Created by 钱小七 on 16/3/14.
 */
public class QiNiu {

    private static final int EXIST_CODE = 614;

    /**
     * 设置好账号的ACCESS_KEY和SECRET_KEY
     */
    protected Auth auth;
    /**
     * 要上传的空间
     */
    public String bucketName;

    /**
     * 创建上传对象
     */
    UploadManager uploadManager;

    public QiNiu(String accessKey, String secretKey, String bucketName) {
        this.bucketName = bucketName;
        this.auth = Auth.create(accessKey, secretKey);
        //机房选择
        Configuration cfg = new Configuration(Zone.autoZone());
        uploadManager = new UploadManager(cfg);
    }

    /**
     * 简单上传，使用默认策略，只需要设置上传的空间名就可以了
     *
     * @return
     */
    public String getUpToken() {
        return auth.uploadToken(bucketName);
    }

    /**
     * 覆盖上传
     *
     * @param key 文件名
     * @return 生成的上传token
     */
    public String getUpToken(String key) {
        return auth.uploadToken(bucketName, key);
    }

    /**
     * 文件上传
     *
     * @param localFilePath 需要上传的文件地址
     * @param newName       上传后新的文件名
     */
    public boolean upload(String localFilePath, String newName) {
        try {
            //调用put方法上传
            Response response = uploadManager.put(localFilePath, newName, getUpToken());
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //打印返回的信息
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return true;
        } catch (QiniuException ex) {
            Response r = ex.response;
            if (r == null) {
                return false;
            } else
                //文件已存在时 使用覆盖上传
                if (r.statusCode == EXIST_CODE) {
                    try {
                        uploadManager.put(localFilePath, newName, getUpToken(newName));
                        return true;
                    } catch (QiniuException ex3) {
                        return false;
                    }
                }
            // 请求失败时打印的异常的信息
            return false;
        }
    }
}
