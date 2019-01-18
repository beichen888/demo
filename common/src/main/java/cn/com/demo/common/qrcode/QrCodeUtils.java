package cn.com.demo.common.qrcode;

import cn.com.demo.common.CommonMessageCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import cn.com.demo.common.exception.AppException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * Created by demo on 15/5/20.
 */
public class QrCodeUtils {
    private final int BLACK = 0xFF000000;
    private final int WHITE = 0xFFFFFFFF;

    private static QrCodeUtils qrCodeUtils;

    private QrCodeUtils(){}

    public static QrCodeUtils getInstance(){
        if(qrCodeUtils == null){
            qrCodeUtils = new QrCodeUtils();
        }
        return qrCodeUtils;
    }
    /**
     * @param content 二维码内容，可以是url
     */
    public byte[] create(String content) throws AppException {
        int width = 300;
        int height = 300;
        //二维码的图片格式
        String format = "gif";
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            throw new AppException(CommonMessageCode.QR_CODE_CREATE_ERROR, "encode error");
        }

        BufferedImage image = toBufferedImage(bitMatrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boolean result;
        try {
            result = ImageIO.write(image, format, baos);
        } catch (IOException e) {
            throw new AppException(CommonMessageCode.QR_CODE_CREATE_ERROR, "qrcode write error");
        }
        if (!result) {
            throw new AppException(CommonMessageCode.QR_CODE_CREATE_ERROR);
        }
        return baos.toByteArray();
    }

    public File create(String content,String path) throws AppException {
        byte[] data = this.create(content);
        //生成二维码
        String folderPath = path.substring(0, path.lastIndexOf("/"));
        File folder = new File(folderPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        File outputFile = new File(path);
        try(FileOutputStream fos = new FileOutputStream(outputFile)){
            fos.write(data);
            fos.flush();
        } catch (IOException e) {
            throw new AppException(CommonMessageCode.QR_CODE_CREATE_ERROR, e);
        }
        return outputFile;
    }

    private BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
}
