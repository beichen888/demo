package cn.com.demo.common.model;

import java.io.Serializable;

/**
 * Created by samurai on 2015/6/3.
 */
public class Email implements Serializable {
    private String title ;
    private String basePath;
    private String sendTo;

    public Email(String domain, String email, String title) {
        this.basePath = domain;
        this.sendTo = email;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getSendTo() {
        return sendTo;
    }
}
