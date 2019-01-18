package cn.com.demo.api.controller;


import cn.com.demo.common.Result;
import cn.com.demo.common.exception.AppException;
import cn.com.demo.api.controller.dto.response.CommentsDto;
import cn.com.demo.api.service.ICommentsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户评论 前端控制器
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
@RestController
@RequestMapping("/api/comments")
public class CommentsController extends BaseController {
    @Resource
    private ICommentsService commentsService;

    @GetMapping(value = "/listByChannel/{channelId}")
    public Result getComments(@PathVariable Long channelId) throws AppException {
        List<CommentsDto> commentsList = commentsService.listByChannelId(channelId);
        return renderSuccess(commentsList);
    }

}