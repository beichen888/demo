package cn.com.demo.api.controller;

import cn.com.demo.common.Result;
import cn.com.demo.common.exception.AppException;
import cn.com.demo.api.common.ProjectConfig;
import cn.com.demo.api.controller.dto.PageDto;
import cn.com.demo.api.controller.dto.response.CommentsDto;
import cn.com.demo.api.entity.*;
import cn.com.demo.api.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miguo on 2019/1/11.
 */
@RestController
@RequestMapping(value = "/api/weixin")
public class WxController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(WxController.class);
    @Resource
    private IUsersService usersService;
    @Resource
    private ProjectConfig projectConfig;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private IChannelService channelService;
    @Resource
    private IMediaService mediaService;
    @Resource
    private ICommentsService commentsService;
    @Resource
    private ICategoriesService categoriesService;

    /**
     * 获取频道类别
     *
     * @return 频道类别
     */
    @GetMapping(value = "/getCategories")
    public Result getCategories() {
        List<Categories> categoriesList = categoriesService.getCategories();
        return renderSuccess(categoriesList);
    }

    /**
     * 首页精选列表
     */
    @GetMapping(value = "/getBestChannels")
    public Result getBest(@RequestParam(defaultValue = "10") Integer size) {
        List<Channel> channelList = channelService.list(null, size, false);
        return renderSuccess(channelList);
    }

    /**
     * 获取频道列表
     *
     * @param categoryId 类别 可以为空
     * @return
     */
    @GetMapping(value = "/getChannels")
    public Result getChannels(Long categoryId, @RequestParam(defaultValue = "1") Integer pageNum) {
        PageDto pageDto = new PageDto();
        pageDto.setPageNum(pageNum);
        pageDto.setPageSize(10);
        Page<Channel> channelList = channelService.listByPage(categoryId, pageDto, false);
        return renderSuccess(channelList);
    }

    /**
     * 频道详情
     *
     * @param channelId
     * @return
     */
    @GetMapping(value = "getChannelById/{channelId}")
    public Result getById(@PathVariable Long channelId) {
        Channel channel = channelService.selectById(channelId);
        List<Media> mediaList = mediaService.listByChannel(channelId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("channel", channel);
        resultMap.put("mediaList", mediaList);
        return renderSuccess(resultMap);
    }

    /**
     * 提交评论
     *
     * @param userId
     * @param channelId
     * @param content
     * @return
     * @throws AppException
     */
    @PostMapping(value = "/addComments")
    public Result add(@RequestParam Long userId, @RequestParam Long channelId, @RequestParam String content, String style) throws AppException {
        commentsService.add(userId, channelId, content, style);
        return renderSuccess();
    }

    /**
     * 评论列表
     *
     * @param channelId
     * @return
     * @throws AppException
     */
    @GetMapping(value = "/getCommentsByChannel/{channelId}")
    public Result getComments(@PathVariable Long channelId) throws AppException {
        List<CommentsDto> commentsList = commentsService.listByChannelId(channelId);
        return renderSuccess(commentsList);
    }

    @PostMapping("/login")
    public Result login(String code) throws AppException {
        Map<String, Object> map = new HashMap<>();
        map.put("appid", projectConfig.getMiniAppId());
        map.put("secret", projectConfig.getMiniSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        AccessToken token = restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}", AccessToken.class, map);
        logger.info("openId:" + token.getOpenid());
        String openId = token.getOpenid();
        if (StringUtils.isBlank(openId)) {
            return renderError("无法登录，请退出重试");
        }
        Users users = usersService.findByOpenId(openId);
        if (users == null) {
            users = new Users();
            users.setOpenId(openId);
            users.preInsert();
            usersService.insert(users);
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("openId",openId);
        resultMap.put("userId",users.getId());
        return renderSuccess(resultMap);
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(String openId, String nickName, String avatarUrl) {
        Users users = usersService.findByOpenId(openId);
        if (users != null) {
            users.setNickname(nickName);
            // 将表情转换成对应别名字符
            users.parseEmojiToAliases();
            users.setAvatarUrl(avatarUrl);
            users.preUpdate();
            usersService.updateById(users);
        }
        return renderSuccess();
    }
}
