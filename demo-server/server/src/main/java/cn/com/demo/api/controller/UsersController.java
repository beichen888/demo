package cn.com.demo.api.controller;


import cn.com.demo.common.Result;
import cn.com.demo.common.exception.AppException;
import cn.com.demo.common.jwt.TokenUtils;
import cn.com.demo.common.jwt.UserToken;
import cn.com.demo.api.controller.dto.PageDto;
import cn.com.demo.api.entity.Users;
import cn.com.demo.api.service.IUsersService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
@RestController
public class UsersController extends BaseController {
    @Resource
    private IUsersService usersService;

    @GetMapping(value = "/api/users/listByPage")
    public Result listByPage(PageDto pageDto) {
        Page<Users> usersPage = usersService.listByPage(pageDto);
        return renderSuccess(usersPage);
    }

    @PostMapping(value = "/api/login")
    public Result login(String username, String password) throws AppException {
        Users user = usersService.userPasswordAuth(username, password);
        UserToken userToken = new UserToken(user.getId(), user.getUserName(), user.getNickname(),
                user.getRole().getValue());
        String token = TokenUtils.sign(userToken);
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("access_token", token);
        return renderSuccess(result);
    }


}

