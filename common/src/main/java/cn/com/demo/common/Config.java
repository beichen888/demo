package cn.com.demo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * User: demo
 * Date: 13-4-15
 * Time: 下午2:48
 */
@Component
public class Config {

    @Value("${web.domain}")
    private String domain;
    @Value("${web.name}")
    private String appName;

    @Value("${role.default.normal:\"\"}")
    private String roleDefault;

    @Value("${upload.folder}")
    private String uploadFolder;

    public String getRoleDefault() {
        return roleDefault;
    }

    public String getUploadFolder() {
        return checkSlash(uploadFolder);
    }

    public String getDomain() {
        return domain;
    }

    public String getAppName() {
        return appName;
    }

    protected String checkSlash(String path){
        if(!path.endsWith(File.separator)){
            return path + "/";
        }else{
            return path;
        }
    }
}
