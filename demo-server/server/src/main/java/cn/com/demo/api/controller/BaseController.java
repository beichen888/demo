package cn.com.demo.api.controller;

import cn.com.demo.common.Result;

/**
 * Created by miguo on 2017/10/11.
 */
public abstract class BaseController {

    /**
     * ajax失败
     *
     * @param msg 失败的消息
     * @return {Object}
     */
    Result renderError(String msg) {
        return new Result<>(false, msg);
    }

    /**
     * ajax成功
     *
     * @return {Object}
     */
    Result renderSuccess() {
        return new Result();
    }

    /**
     * ajax成功
     *
     * @param msg 消息
     * @return {Object}
     */
    Result renderSuccess(String msg) {
        return new Result<>(true, msg);
    }

    /**
     * ajax成功
     *
     * @param obj 成功时的对象
     * @return {Object}
     */
    Result renderSuccess(Object obj) {
        return new Result<>(true, obj);
    }

}
