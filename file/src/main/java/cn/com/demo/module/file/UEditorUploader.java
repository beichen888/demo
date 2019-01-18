package cn.com.demo.module.file;

import cn.com.demo.module.file.model.PlainFile;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * UEditor文件上传辅助类
 */
public class UEditorUploader implements IFileUploader{
	private PlainFile file;
	// 文件允许格式
	private String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf",".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
	// 文件大小限制，单位KB
	private int maxSize = 10000;

	private HttpServletRequest request = null;

	private HashMap<String, String> errorInfo = new HashMap<>();

	public UEditorUploader(HttpServletRequest request) {
        file = new PlainFile();
		this.request = request;
		this.errorInfo.put("NOFILE", "未包含文件上传域");
		this.errorInfo.put("TYPE", "不允许的文件格式");
		this.errorInfo.put("SIZE", "文件大小超出限制");
		this.errorInfo.put("ENTYPE", "请求类型ENTYPE错误");
		this.errorInfo.put("REQUEST", "上传请求异常");
		this.errorInfo.put("IO", "IO异常");
		this.errorInfo.put("DIR", "目录创建失败");
		this.errorInfo.put("UNKNOWN", "未知错误");
	}

	@Override
	public String upload(File file, String uploadFileName) {
		return this.errorInfo.get("UNKNOWN");
	}

	public String upload() {
		String state = "SUCCESS";
		if (!ServletFileUpload.isMultipartContent(this.request)) {
			state = this.errorInfo.get("NOFILE");
			return state;
		}

		DiskFileItemFactory dff = new DiskFileItemFactory();
		String savePath = file.getFolder(getPhysicalPath());
		dff.setRepository(new File(savePath));
		try {
			ServletFileUpload sfu = new ServletFileUpload(dff);
			sfu.setSizeMax(maxSize * 1024);
			sfu.setHeaderEncoding("utf-8");
			FileItemIterator fii = sfu.getItemIterator(this.request);
			while (fii.hasNext()) {
				FileItemStream fis = fii.next();
				if (!fis.isFormField()) {
					String originalName = fis.getName().substring(fis.getName().lastIndexOf(System.getProperty("file.separator")) + 1);
					file.setOriginalName(originalName);
					if (!this.checkFileType(originalName)) {
						state = this.errorInfo.get("TYPE");
						continue;
					}
					String url = savePath + "/" + file.getName();
					file.setUrl(url);
					BufferedInputStream in = new BufferedInputStream(fis.openStream());
					File tmpFile = new File(this.getPhysicalPath()+url);
					FileOutputStream out = new FileOutputStream(tmpFile);
					BufferedOutputStream output = new BufferedOutputStream(out);
					Streams.copy(in, output, true);
					file.setSize(tmpFile.length());
					//UE中只会处理单张上传，完成后即退出
					break;
				} else {
					String fname = fis.getFieldName();
					//只处理title，其余表单请自行处理
					if(!"pictitle".equals(fname)){
						continue;
					}
                    BufferedInputStream in = new BufferedInputStream(fis.openStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    while (reader.ready()) {  
                        result.append((char)reader.read());  
                    }
                    file.setTitle(new String(result.toString().getBytes(), "utf-8"));
                    reader.close();
				}
			}
		} catch (SizeLimitExceededException e) {
			state = this.errorInfo.get("SIZE");
		} catch (InvalidContentTypeException e) {
			state = this.errorInfo.get("ENTYPE");
		} catch (FileUploadException e) {
			state = this.errorInfo.get("REQUEST");
		} catch (Exception e) {
			state = this.errorInfo.get("UNKNOWN");
		}

		return state;
	}
	
	/**
	 * 接受并保存以base64格式上传的文件
	 */
	public String uploadBase64(String fieldName){
		String savePath = file.getFolder(getPhysicalPath());
		String base64Data = this.request.getParameter(fieldName);
		String fileName = file.getName();
		String url = savePath + "/" + fileName;
		Base64 base64 = new Base64();
		String state = "SUCCESS";

		File outFile = new File(this.getPhysicalPath()+url);
		try(OutputStream outputStream = new FileOutputStream(outFile)){
			byte[] b = base64.decode(base64Data);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			outputStream.write(b);
			outputStream.flush();
		}catch (IOException e){
			state = this.errorInfo.get("IO");
		}
		return state;
	}

	/**
	 * 文件类型判断
	 * @return true表示在允许的列表中
	 */
	private boolean checkFileType(String fileName) {
		for (String ext : Arrays.asList(this.allowFiles)) {
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据传入的虚拟路径获取物理路径
	 */
	private String getPhysicalPath() {
        String contextPath = this.request.getContextPath();
        String realPath = this.request.getSession().getServletContext().getRealPath(contextPath);
		return new File(realPath).getParent()+"/";
	}

	public void setAllowFiles(String[] allowFiles) {
		this.allowFiles = allowFiles;
	}

	public PlainFile getFile() {
		return file;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
