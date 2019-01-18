package cn.com.demo.api.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessToken implements Serializable {
    private String openid;
    private String session_key;
    private String unionid;
    private Integer errcode;
    private String errmsg;
}
