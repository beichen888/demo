package cn.com.demo.api.service;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.api.entity.Media;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 音频图片信息 服务类
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
public interface IMediaService extends IService<Media> {

    List<Media>  listByChannel(Long channelId);

    void importMedia(MultipartFile file, Long channelId) throws AppException;
}
