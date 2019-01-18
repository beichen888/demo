package cn.com.demo.module.file.common;

import cn.com.demo.common.IMsgCode;

/**
 * Created by long on 2015/1/16.
 */
public enum FileMessageCode implements IMsgCode {
    FILE_NAME_BLANK,
    FILE_NOT_EXIST,
    FILE_MOVE_FAILED,
    FILE_DELETE_FAILED,
    FILE_IS_OVER_SIZE,

    FOLDER_EXIST,
    FOLDER_NULL,
    FOLDER_PARENT_IS_SUB_FOLDER,
    FOLDER_NOT_EXIST,
    FOLDER_MOVE_FAILED,
    FOLDER_DELETE_FAILED,
    FOLDER_CREATE_FAILED,

    STORAGE_IS_OVER_SIZE,
    FILE_UPLOAD_ERROR,
    FILE_NOT_EXIST_ERROR,

    FILE_IMPORT_ERROR,
    FILE_READ_ERROR,
}
