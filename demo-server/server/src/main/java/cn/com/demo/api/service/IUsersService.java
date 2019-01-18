package cn.com.demo.api.service;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.api.controller.dto.PageDto;
import cn.com.demo.api.entity.Users;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.data.domain.Page;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
public interface IUsersService extends IService<Users> {
    Page<Users> listByPage(PageDto pageDto);

    Users findByOpenId(String openId);

    Users userPasswordAuth(String username, String password) throws AppException;

    Users findByUserName(String userName);
}
