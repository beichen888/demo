package weixin.popular.util;

import cn.com.demo.common.CommonMessageCode;
import cn.com.demo.common.Result;
import cn.com.demo.common.exception.AppException;
import weixin.popular.bean.WeiXinResponse;

public class ErrorResponseUtil {
    public static void handleErrorResponse(WeiXinResponse response) throws AppException {
        if(response.getErrcode().equals(43004)){
            throw new AppException(CommonMessageCode.WEIXIN_NOT_FOLLOW_PUBLIC_NUM_ERROR);
        }else if(!response.getErrcode().equals(0)){
            throw new AppException(CommonMessageCode.WEIXIN_RESPONSE_ERROR, response);
        }
    }
}

