package cn.com.demo.module.file;

import cn.com.demo.common.exception.AppException;

import java.io.File;

/**
 * Created by long on 2015/1/15.
 */
public interface IFileUploader {
    /**
     * 上传文件
     *
     * @param uploadFileName 保存新的文件名，如为空，则保存原文件名
     * @param file   文件
     * @return
     * @throws AppException
     */
    String upload(File file, String uploadFileName) throws AppException;
}
