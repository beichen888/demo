package cn.com.demo.api.service.impl;

import cn.com.demo.api.common.ProjectConfig;
import cn.com.demo.api.controller.dto.PageDto;
import cn.com.demo.api.entity.Channel;
import cn.com.demo.api.entity.Media;
import cn.com.demo.api.mapper.ChannelMapper;
import cn.com.demo.api.mapper.MediaMapper;
import cn.com.demo.api.service.IChannelService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 频道信息 服务实现类
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ChannelService extends BaseServiceImpl<ChannelMapper, Channel> implements IChannelService {

    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private MediaMapper mediaMapper;
    @Resource
    private ProjectConfig projectConfig;

    @Override
    public Page<Channel> listByPage(Long categoryId, PageDto pageDto, Boolean orderByAsc) {
        Wrapper<Channel> wrapper = new EntityWrapper<>();
        if (orderByAsc != null) {
            wrapper.orderBy("id", orderByAsc);
        } else {
            wrapper.orderBy("id", false);
        }
        if (categoryId != null) {
            wrapper.where("category_id = {0}", categoryId);
        }
        PageHelper.startPage(pageDto.getPageNum(), pageDto.getPageSize());
        List<Channel> channelList = channelMapper.selectList(wrapper);
        long count = new PageInfo<>(channelList).getTotal();
        return new PageImpl<>(channelList, PageRequest.of(pageDto.getPageNum() - 1, pageDto.getPageSize()), count);
    }

    @Override
    public List<Channel> list(Long categoryId, Integer limit, Boolean orderByAsc) {
        Wrapper<Channel> wrapper = new EntityWrapper<>();
        if (orderByAsc != null) {
            wrapper.orderBy("id", orderByAsc);
        } else {
            wrapper.orderBy("id", false);
        }
        if (categoryId != null) {
            wrapper.where("category_id = {0}", categoryId);
        }
        if (limit != null && limit > 0) {
            PageHelper.startPage(1, limit);
        }
        List<Channel> channelList = channelMapper.selectList(wrapper);
        return channelList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(Long[] ids) {
        Channel channel;
        for (Long id : ids) {
            channel = channelMapper.selectById(id);
            if (channel != null) {
                //删除音频
                Wrapper<Media> mediaWrapper = new EntityWrapper<>();
                mediaWrapper.where("channel_id = {0}", channel.getId());
                mediaMapper.delete(mediaWrapper);
                //删除频道
                channelMapper.deleteById(channel.getId());
                String uploadPath = projectConfig.getUploadFolder() + projectConfig.getAudioFolder() + channel.getId() + "/";
                File targetDirectory = new File(uploadPath);
                try {
                    FileUtils.deleteDirectory(targetDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
