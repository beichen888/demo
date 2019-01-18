package cn.com.demo.common.file;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by demo on 15/12/9.
 */
public class Downloader {
    public static void downloadFromUrl(String filePath, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        InputStream is = null;
        OutputStream os = null;
        try {
            // 构造URL
            URL url = new URL(filePath);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 输入流
            is = con.getInputStream();
            os = response.getOutputStream();

            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
