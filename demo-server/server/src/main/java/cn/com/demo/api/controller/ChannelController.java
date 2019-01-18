package cn.com.demo.api.controller;


import cn.com.demo.common.Result;
import cn.com.demo.common.Utils;
import cn.com.demo.common.exception.AppException;
import cn.com.demo.api.common.ProjectConfig;
import cn.com.demo.api.controller.dto.PageDto;
import cn.com.demo.api.entity.Channel;
import cn.com.demo.api.entity.Media;
import cn.com.demo.api.service.IChannelService;
import cn.com.demo.api.service.IMediaService;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频道信息 前端控制器
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
@RestController
@RequestMapping("/api/channel")
public class ChannelController extends BaseController {

    @Resource
    private Validator validator;
    @Resource
    private IChannelService channelService;
    @Resource
    private IMediaService mediaService;
    @Resource
    private ProjectConfig projectConfig;


    @GetMapping(value = "/listByPage")
    public Result listByPage(Long categoryId, PageDto pageDto) {
        Page<Channel> page = channelService.listByPage(categoryId, pageDto, false);
        return renderSuccess(page);
    }

    @GetMapping(value = "/{channelId}")
    public Result getById(@PathVariable Long channelId) {
        Channel channel = channelService.selectById(channelId);
        List<Media> mediaList = mediaService.listByChannel(channelId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("channel", channel);
        resultMap.put("mediaList", mediaList);
        return renderSuccess(resultMap);
    }

    @PostMapping(value = "/add")
    public Result add(Channel channel) throws AppException {
        channel.preInsert();
        channel.validate(validator);
        channelService.insert(channel);
        return renderSuccess();
    }

    @PostMapping(value = "/edit/{channelId}")
    public Result edit(@PathVariable Long channelId, Channel channel) throws AppException {
        channel.setId(channelId);
        channel.preUpdate();
        channel.validate(validator);
        channelService.updateById(channel);
        return renderSuccess();
    }

    @PostMapping(value = "/importAudio/{channelId}")
    public Result importAudio(@PathVariable Long channelId, MultipartFile file) throws ZipException, AppException {
        mediaService.importMedia(file, channelId);
        return renderSuccess();
    }

    @PostMapping(value = "/uploadFile")
    public Result uploadFile(MultipartFile file) throws IOException, AppException {
        String uploadFile = projectConfig.getUploadFolder() + projectConfig.getCoverFolder();
        String fileName = Utils.upload(uploadFile, file);
        String fileUrl = projectConfig.getMediaRootUrl() + projectConfig.getCoverFolder() + fileName;
        return renderSuccess(fileUrl);
    }

    @PostMapping(value = "/deleteByIds")
    public Result deleteByIds(@RequestParam(value = "ids") Long[] ids) {
        channelService.deleteByIds(ids);
        return renderSuccess();
    }
}

