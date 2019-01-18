package cn.com.demo.api.common;


import cn.com.demo.common.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by demo on 2015/12/15.
 */
@Component
public class ProjectConfig extends Config {

    @Value("${mini.appid}")
    private String miniAppId;
    @Value("${mini.secret}")
    private String miniSecret;
    @Value("${file.audioFolder}")
    private String audioFolder;
    @Value("${file.coverFolder}")
    private String coverFolder;


    /**
     * 微信小程序app id
     *
     * @return
     */
    public String getMiniAppId() {
        return miniAppId;
    }

    /**
     * 微信小程序 secret
     *
     * @return
     */
    public String getMiniSecret() {
        return miniSecret;
    }

    public String getAudioFolder() {
        return checkSlash(audioFolder);
    }

    public String getCoverFolder() {
        return checkSlash(coverFolder);
    }

    public String getMediaRootUrl() {
        return checkSlash(getDomain()) + "media/";
    }

}