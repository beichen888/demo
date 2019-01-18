package cn.com.demo.module.file.storage.upyun;

import cn.com.demo.common.CommonMessageCode;
import cn.com.demo.common.exception.AppException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Calendar;

/**
 * Created by long on 2015/1/16.
 */
@JsonIgnoreProperties({"formApi","policy","signature"})
public class UpyunForm {
    private String bucket;

    @JsonProperty("save-key")
    private String saveKey;

    /**
     * 过期时间(秒)
     */
    private long expiration;

    private String formApi;

    public UpyunForm(){
    }

    public UpyunForm(String bucket, String saveKey, long expiration, String formApi){
        this.bucket = bucket;
        this.saveKey = saveKey;
        this.expiration = expiration;
        this.formApi = formApi;
    }

    public UpyunForm(String bucket, String saveKey, String formApi){
        this.bucket = bucket;
        this.saveKey = saveKey;
        //12小时
        this.expiration = (Calendar.getInstance().getTimeInMillis()/1000) + 60*60*12;
        this.formApi = formApi;
    }

    public String getSaveKey() {
        return saveKey;
    }

    public void setSaveKey(String saveKey) {
        this.saveKey = saveKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setFormApi(String formApi) {
        this.formApi = formApi;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    /**
     * 生成policy
     * @return policy
     */
    public String getPolicy() throws AppException {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new AppException(CommonMessageCode.SERVER_ERROR, e);
        }
        Base64 encoder = new Base64();
        return new String(encoder.encode(json.getBytes()));
    }

    /**
     * 生成签名
     * @return 签名
     */
    public String getSignature() throws AppException {
        String str = getPolicy() + "&" + formApi;
        return DigestUtils.md5Hex(str);
    }
}
