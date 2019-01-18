package cn.com.demo.api.service;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.api.controller.dto.response.CommentsDto;
import cn.com.demo.api.entity.Comments;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户评论 服务类
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
public interface ICommentsService extends IService<Comments> {

    List<CommentsDto> listByChannelId(Long channelId) throws AppException;

    void add(Long userId, Long channelId, String content, String style) throws AppException;

}
