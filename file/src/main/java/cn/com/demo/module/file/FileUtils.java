package cn.com.demo.module.file;

import cn.com.demo.common.Utils;
import cn.com.demo.common.exception.AppException;
import cn.com.demo.module.file.common.FileMessageCode;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by long on 2015/1/15.
 */
public class FileUtils {
    public static <T> List<T> importExcelZip(MultipartFile multipartFile, String path, IExcelImport excelImport) throws AppException, ZipException {
        String filename = multipartFile.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        if (!"zip".equals(ext.toLowerCase())) {
            throw new AppException(FileMessageCode.FILE_IMPORT_ERROR);
        }

        String fileName = Utils.upload(path, multipartFile);
        ZipUtil.unzip(new File(path + fileName), path, null);

        File folder = new File(path);
        List list = new ArrayList();
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            boolean hasExcelFile = false;
            try {
                for (File file : files) {
                    if (file.isFile()) {
                        fileName = file.getName();
                        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                        if ("xlsx".equals(prefix) || "xls".equals(prefix)) {  //所有后缀为xlsx的文件
                            hasExcelFile = true;
                            List excelList = excelImport.parseExcel(file);
                            list.addAll(excelList);
                        }
                    }
                }
                if (!hasExcelFile) {
                    // 没有excel文件时，报错
                    throw new AppException(FileMessageCode.FILE_NOT_EXIST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                for (File file : files) {
                    if (file.isFile()) {
                        fileName = file.getName();
                        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                        if ("xlsx".equals(prefix) || "xls".equals(prefix)) {  //所有后缀为xlsx的文件
                            file.delete();
                        }
                    }
                }
            }
        }
        return list;
    }
}
