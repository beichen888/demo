package cn.com.demo.api.service;

import cn.com.demo.api.controller.dto.PageDto;
import cn.com.demo.api.entity.Channel;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>
 * 频道信息 服务类
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
public interface IChannelService extends IService<Channel> {

    Page<Channel> listByPage(Long categoryId, PageDto pageDto, Boolean orderByAsc);

    List<Channel> list(Long categoryId, Integer limit, Boolean orderByAsc);

    void deleteByIds(Long[] ids);

}
