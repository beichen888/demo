package cn.com.demo.api.common;

import cn.com.demo.common.IMsgCode;

/**
 * User: sunlong
 * Date: 13-4-15
 * Time: 下午2:42
 */
public enum MessageCode implements IMsgCode {
    CONSUL_SERVER_NOT_EXIST_ERROR,
    PARAMETER_ERROR,
    TOKEN_NOT_CORRECT_ERROR,
    TOKEN_TIME_OUT_ERROR,
    FILE_POSTFIX_ERROR,
    FILE_NOT_EXIT_ERROR,
    ROW_FORMAT_ERROR,
    RPC_ERROR,

    /**
     * 本项目自定义异常
     */
    CODE_EXIST_ERROR,
    COMMENTS_NOT_EXIST_ERROR,
    CHANNEL_NOT_EXIST_ERROR,
    LEARN_RECORD_NOT_EXIST_ERROR,
    EXPORT_PDF_ERROR,
    TEST_HAS_SUBMIT_ERROR,
    PASSWORD_ERROR,
    USER_NOT_EXIST_ERROR,
    MEDIA_IMPORT_ERROR,
}
