package cn.com.demo.api.util;

import cn.com.demo.common.CommonMessageCode;
import cn.com.demo.common.ConfigConstant;
import cn.com.demo.common.Digests;
import cn.com.demo.common.exception.AppException;
import cn.com.demo.common.jwt.UserToken;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

public class ProjectUtil {

    public static UserToken getUserToken() throws AppException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserToken userToken = (UserToken) request.getAttribute("user");
        return userToken;
    }

    public static String joinParam(Map<String, String> param, boolean urlencode) {
        // 对参数名进行字典排序
        String[] keyArray = param.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

        // 拼接有序的参数名-值串
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keyArray) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append("&");
            }
            String value = param.get(key);
            if (urlencode) {
                try {
                    value = URLEncoder.encode(value, null);
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }
            stringBuilder.append(key).append("=").append(value);
        }
        return stringBuilder.toString();
    }

    public static String hashString(String password, String salt) throws AppException {
        try {
            return Hex.encodeHexString(Digests.sha1(password.getBytes(), Hex.decodeHex(salt.toCharArray()), ConfigConstant.hashIterations));
        } catch (DecoderException e) {
            throw new AppException(CommonMessageCode.HEX_DECODE_EXCEPTION_ERROR);
        }
    }

}
