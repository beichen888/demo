package cn.com.demo.common;

import cn.com.demo.common.exception.AppException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * User: demo
 */
public class Utils {
    /**
     * 找出在a中但是不在b中的数据
     */
    public static List<String> inANotInB(String[] a, String[] b){
        List<String> c = new ArrayList<>();
        for(String itemA: a){
            boolean exist = false;
            for (String itemB: b){
                if(itemA.equals(itemB)){//相等
                    exist = true;
                }
            }
            if(!exist){
                c.add(itemA);
            }
        }
        return c;
    }

    /**
     * 上传文件
     * @param folder 上传的根目录 如c:/tmp/
     * @param file 文件
     * @return 文件名，带相对路径，如 2014/8/10/abcd.jpg
     * @throws AppException
     */
    public static String upload(String folder, MultipartFile file) throws AppException {
        if(!folder.endsWith(File.separator)){
            folder = folder + "/";
        }

        if(!new File(folder).exists()){
            new File(folder).mkdirs();
        }

        String name = file.getOriginalFilename();
        // Make sure the fileName is unique
        if(new File(folder + name).exists()){
            int index = name.lastIndexOf(".");
            String fileName = name.substring(0, index);
            String ext = name.substring(index);
            int count = 1;
            File aFile = new File(folder + fileName + "_" + count + ext);
            while (aFile.exists()){
                count++;
                aFile = new File(folder + fileName + "_" + count + ext);
            }
            name = fileName + "_" + count + ext;
        }

        String filePath = folder + name;

        if(!file.isEmpty()){//如果不空
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                throw new AppException(CommonMessageCode.FILE_WRITE_ERROR, e.getMessage());
            }
        }
        return filePath.substring(folder.length());
    }

    /**
     * 上传录音文件
     * @param folder 上传的根目录 如c:/tmp/
     * @param file 文件
     * @return 文件名，带相对路径，如 2014/8/10/abcd.jpg
     * @throws AppException
     */
    public static String uploadRecorder(String folder, MultipartFile file) throws AppException {
        if(!folder.endsWith(File.separator)){
            folder = folder + "/";
        }

        if(!new File(folder).exists()){
            new File(folder).mkdirs();
        }

        String name = file.getOriginalFilename()+".wav";
        // Make sure the fileName is unique
        if(new File(folder + name).exists()){
            int index = name.lastIndexOf(".");
            String fileName = name.substring(0, index);
            String ext = name.substring(index);
            int count = 1;
            File aFile = new File(folder + fileName + "_" + count + ext);
            while (aFile.exists()){
                count++;
                aFile = new File(folder + fileName + "_" + count + ext);
            }
            name = fileName + "_" + count + ext;
        }

        String filePath = folder + name;

        if(!file.isEmpty()){//如果不空
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                throw new AppException(CommonMessageCode.FILE_WRITE_ERROR, e.getMessage());
            }
        }
        return filePath.substring(folder.length());
    }

    /**
     * 发送邮件
     * 该方法已废弃，请使用EmailUtils.sendHtmlEmail()
     */
    @Deprecated
    public static void sendHtmlEmail(String emailTitle,String emailContent,String email) throws Exception {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

        //设定mail server
        senderImpl.setHost("smtp.163.com");

        //建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);

        //设置收件人，寄件人
        messageHelper.setTo(email);
        messageHelper.setFrom("demo321@163.com");
        messageHelper.setSubject(emailTitle);
        messageHelper.setText(emailContent);

        senderImpl.setUsername("demo321@163.com") ; // 根据自己的情况,设置username
        senderImpl.setPassword("demo123") ; // 根据自己的情况, 设置password
        Properties prop = new Properties() ;
        prop.put("mail.smtp.auth", "true") ; // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.timeout", "25000") ;
        senderImpl.setJavaMailProperties(prop);
        //发送邮件
        senderImpl.send(mailMessage);
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true"
     *
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 复制目录
     */
    public static void copyDir(String inputname, String outputname) throws AppException {
        try {
            //mkdir if destination does not exist
            File outtest = new File(outputname);
            if (!outtest.exists()) {
                outtest.mkdir();
            }

            File[] files = (new File(inputname)).listFiles();
            for (File aFile : files) {
                if (aFile.isFile()) {
                    FileInputStream input = new FileInputStream(aFile);

                    FileOutputStream output = new FileOutputStream(outputname + "/" + (aFile.getName()));
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                } else if (aFile.isDirectory()) {
                    copyDir(aFile.toString(), outputname + "//" + aFile.getName());
                }
            }
        } catch(Exception e) {
            throw new AppException(CommonMessageCode.SERVER_ERROR);
        }
    }

    /**
     * @param excelHeader 表格的列头(第一行)
     * @param content  表格的列内容
     * @throws IOException
     */
    public static void exportExcel(HttpServletResponse response,List<String> excelHeader,List<List<String>> content)throws IOException{

        HSSFWorkbook wb = new HSSFWorkbook();
        int sheetSize=content.size()/60000+1;
        for(int i=1;i<=sheetSize;i++){
            createSheet(wb,excelHeader,i,content);
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=user.xls");
        OutputStream outputStream = response.getOutputStream();

        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private static void createSheet(HSSFWorkbook wb,List<String> excelHeader,int sheetPage,List<List<String>> content){
        HSSFSheet sheet = wb.createSheet("Sheet"+sheetPage);
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        for (int i = 0; i < excelHeader.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader.get(i));
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,5000);
        }
        int dataSize=sheetPage*60000;
        if(content.size()<sheetPage*60000){
            dataSize=content.size();
        }
        for(int i=(sheetPage-1)*60000;i<dataSize;i++){
            row = sheet.createRow(i + 1 -(sheetPage-1)*60000);
            for (int j=0;j<content.get(i).size();j++){
                String value=content.get(i).get(j);
                if (value != null){
                    switch (value) {
                        case "false":
                            row.createCell(j).setCellValue("否");
                            break;
                        case "true":
                            row.createCell(j).setCellValue("是");
                            break;
                        default:
                            row.createCell(j).setCellValue(value);
                            break;
                    }
                }else{
                    row.createCell(j).setCellValue(" ");
                }
            }

        }
    }

    /**
     * 下个版本会删除
     * @param list 需要显示的数据集合
     * @param excelHeader 表格的列头(第一行)
     * @param content  表格的列内容
     * @throws IOException
     */
    @Deprecated
    public static void exportExcel(HttpServletResponse response,List<?> list,List<String> excelHeader,List<List<String>> content)throws IOException{
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Sheet1");
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        for (int i = 0; i < excelHeader.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader.get(i));
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,5000);
        }
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            for (int j=0;j<content.get(i).size();j++){
                String value=content.get(i).get(j);
                if (value != null){
                    if (value.equals("false")){
                        row.createCell(j).setCellValue("否");
                    }else if (value.equals("true")){
                        row.createCell(j).setCellValue("是");
                    }else{
                        row.createCell(j).setCellValue(value);
                    }
                }else{
                    row.createCell(j).setCellValue(" ");
                }

            }
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=user.xls");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 获取 ip 地址
     */
    public static String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if(ip != null && ip.length()>15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }

        return ip;
    }

}
