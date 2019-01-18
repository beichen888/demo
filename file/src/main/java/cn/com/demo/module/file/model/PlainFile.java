package cn.com.demo.module.file.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by long on 2015/1/15.
 */
public class PlainFile {
    // 输出文件地址
    private String url = "";
    // 上传文件名
    private String fileName = "";
    // 原始文件名
    private String originalName = "";
    // 文件大小
    private long size = 0;

    private String title = "";
    // 保存路径
    private String savePath = "upload";

    /**
     * 根据字符串创建本地目录 并按照日期建立子目录返回
     * @return relative path
     */
    public String getFolder(String physicalPath) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String path = savePath + "/" + dateFormat.format(new Date());
        File dir = new File(physicalPath + path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    /**
     * 依据原始文件名生成新文件名
     * @return
     */
    public String getName() {
        Random random = new Random();
        this.fileName = random.nextInt(10000) + System.currentTimeMillis() + this.getType();
        return this.fileName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public long getSize() {
        return size;
    }

    /**
     * 获取文件扩展名
     *
     * @return string
     */
    public String getType() {
        return originalName.substring(originalName.lastIndexOf("."));
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
