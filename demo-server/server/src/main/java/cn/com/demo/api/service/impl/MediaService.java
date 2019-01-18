package cn.com.demo.api.service.impl;

import cn.com.demo.common.Utils;
import cn.com.demo.common.excel.ExcelRow;
import cn.com.demo.common.excel.ExcelSheet;
import cn.com.demo.common.excel.ExcelUtil;
import cn.com.demo.common.exception.AppException;
import cn.com.demo.module.file.ZipUtil;
import cn.com.demo.module.file.common.FileMessageCode;
import cn.com.demo.api.common.MessageCode;
import cn.com.demo.api.common.ProjectConfig;
import cn.com.demo.api.entity.Media;
import cn.com.demo.api.mapper.MediaMapper;
import cn.com.demo.api.service.IMediaService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 音频图片信息 服务实现类
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MediaService extends BaseServiceImpl<MediaMapper, Media> implements IMediaService {

    @Resource
    private Validator validator;
    @Resource
    private ProjectConfig projectConfig;
    @Resource
    private MediaMapper mediaMapper;

    @Override
    public List<Media> listByChannel(Long channelId) {
        Wrapper<Media> wrapper = new EntityWrapper<>();
        wrapper.where("channel_id = {0}", channelId);
        wrapper.orderBy("sort_no", true);
        List<Media> list = mediaMapper.selectList(wrapper);
        for (Media media : list) {
            media.setUrl(projectConfig.getMediaRootUrl() + projectConfig.getAudioFolder() + media.getUrl());
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importMedia(MultipartFile zipFile, Long channelId) throws AppException {
        String filename = zipFile.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        if (!"zip".equals(ext.toLowerCase())) {
            throw new AppException(MessageCode.FILE_POSTFIX_ERROR);
        }
        String uploadPath = projectConfig.getUploadFolder() + projectConfig.getAudioFolder() + channelId + "/";
        File targetDirectory = new File(uploadPath);
        // 删除原有数据
        deleteByChannelId(channelId);
        //删除原有的文件
        if (targetDirectory.exists()) {
            try {
            FileUtils.deleteDirectory(targetDirectory);
            targetDirectory.mkdirs();
            } catch (IOException e) {
                throw new AppException(MessageCode.MEDIA_IMPORT_ERROR);
            }
        }

        String fileName = Utils.upload(uploadPath, zipFile);
        try {
            ZipUtil.unzip(uploadPath + fileName, null);
        } catch (ZipException e) {
            throw new AppException(MessageCode.MEDIA_IMPORT_ERROR);
        }
        //删除zip文件
        File saveZipFile = new File(uploadPath+fileName);
        saveZipFile.delete();
        File[] files = targetDirectory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                fileName = file.getName();
                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                //所有后缀为xlsx的文件
                if ("xlsx".equals(prefix) || "xls".equals(prefix)) {
                    parseExcel(file.getAbsolutePath(), channelId);
                    file.delete();
                    return;
                }
            }
        }
    }

    private void deleteByChannelId(Long channelId) {
        Wrapper<Media> wrapper = new EntityWrapper<>();
        wrapper.where("channel_id = {0}", channelId);
        mediaMapper.delete(wrapper);
    }

    private void parseExcel(String excelPath, Long channelId) throws AppException {
        ExcelSheet sheet;
        try {
            List<ExcelSheet> sheets = ExcelUtil.readExcel(excelPath);
            if (sheets != null && !sheets.isEmpty()) {
                sheet = sheets.get(0);
            } else {
                throw new AppException(FileMessageCode.FILE_READ_ERROR);
            }
            List<ExcelRow> excelRows = sheet.getExcelRows();

            if (excelRows == null) {
                return;
            }
            //导入数据库
            int rowIndex = 0;
            for (ExcelRow excelRow : excelRows) {
                rowIndex++;
                if (rowIndex == 1) {
                    continue;
                }
                String audioName = excelRow.getCellValue(0);
                String audioUrl = excelRow.getCellValue(1);
                String sortNo = excelRow.getCellValue(2);
                Media media = new Media();
                media.setChannelId(channelId);
                media.setTitle(audioName);

                media.setUrl(channelId + "/" + audioUrl);
                if (StringUtils.isNumeric(sortNo)) {
                    media.setSortNo(Integer.valueOf(sortNo));
                }
                media.preInsert();
                media.validate(validator);
                mediaMapper.insert(media);
            }
        } catch (IOException e) {
            throw new AppException(FileMessageCode.FILE_READ_ERROR);
        }
    }
}
