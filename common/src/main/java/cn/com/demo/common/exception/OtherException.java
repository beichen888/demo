package cn.com.demo.common.exception;

import cn.com.demo.common.IMsgCode;

/**
 * Created by skydream1 on 2014/9/14.
 */
public class OtherException extends Exception {
    private Object[] args;
    private IMsgCode msgCode;

    public OtherException() {

    }

    public OtherException(IMsgCode msgCode, Object... args) {
        this.args = args;
        this.msgCode = msgCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public IMsgCode getMsgCode() {
        return msgCode;
    }
}
