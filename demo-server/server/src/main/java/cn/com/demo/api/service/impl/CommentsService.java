package cn.com.demo.api.service.impl;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.api.controller.dto.response.CommentsDto;
import cn.com.demo.api.entity.Comments;
import cn.com.demo.api.entity.Users;
import cn.com.demo.api.mapper.CommentsMapper;
import cn.com.demo.api.mapper.UsersMapper;
import cn.com.demo.api.service.ICommentsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户评论 服务实现类
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CommentsService extends BaseServiceImpl<CommentsMapper, Comments> implements ICommentsService {
    @Resource
    private Validator validator;
    @Resource
    private CommentsMapper commentsMapper;

    @Resource
    private UsersMapper usersMapper;

    @Override
    public List<CommentsDto> listByChannelId(Long channelId) throws AppException {
        Wrapper<Comments> wrapper = new EntityWrapper<>();
        wrapper.orderBy("id", false);
        wrapper.where("channel_id = {0}", channelId);
        List<Comments> commentsList = commentsMapper.selectList(wrapper);
        List<CommentsDto> commentsDtoList = new ArrayList<>();
        for (Comments comments : commentsList) {
            CommentsDto commentsDto = new CommentsDto();
            BeanUtils.copyProperties(comments, commentsDto);
            Users users = usersMapper.selectById(comments.getUserId());
            if (users != null) {
                // 再次转换回表情
                users.parseEmojiToUnicode();
                commentsDto.setNickName(users.getNickname());
                commentsDto.setAvatarUrl(users.getAvatarUrl());
            }
            commentsDtoList.add(commentsDto);
        }
        return commentsDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Long userId, Long channelId, String content, String style) throws AppException {
        Comments comments = new Comments();
        comments.setUserId(userId);
        comments.setChannelId(channelId);
        comments.setContent(content);
        comments.setStyle(style);
        comments.preInsert();
        comments.validate(validator);
        commentsMapper.insert(comments);
    }
}
