package cn.com.demo.api.service.impl;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.api.common.MessageCode;
import cn.com.demo.api.controller.dto.PageDto;
import cn.com.demo.api.entity.Users;
import cn.com.demo.api.mapper.UsersMapper;
import cn.com.demo.api.service.IUsersService;
import cn.com.demo.api.util.ProjectUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UsersService extends BaseServiceImpl<UsersMapper, Users> implements IUsersService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public Page<Users> listByPage(PageDto pageDto) {
        Wrapper<Users> wrapper = new EntityWrapper<>();
        wrapper.orderBy("id", true);
        PageHelper.startPage(pageDto.getPageNum(), pageDto.getPageSize());
        List<Users> usersList = usersMapper.selectList(wrapper);
        long count = new PageInfo<>(usersList).getTotal();
        return new PageImpl<>(usersList, PageRequest.of(pageDto.getPageNum() - 1, pageDto.getPageSize()), count);
    }

    @Override
    public Users findByOpenId(String openId) {
        Wrapper<Users> wrapper = new EntityWrapper<>();
        wrapper.where("open_id = {0}", openId);
        List<Users> usersList = usersMapper.selectList(wrapper);
        if (!usersList.isEmpty()) {
            return usersList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Users userPasswordAuth(String username, String password) throws AppException {
        Users user = findByUserName(username);
        if (user == null) {
            throw new AppException(MessageCode.USER_NOT_EXIST_ERROR, username);
        }
        String newPassword = ProjectUtil.hashString(password, user.getSalt());
        if (!newPassword.equals(user.getPassword())) {
            throw new AppException(MessageCode.PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    public Users findByUserName(String userName) {
        Wrapper<Users> wrapper = new EntityWrapper<>();
        wrapper.where("user_name = {0}", userName);
        List<Users> usersList = usersMapper.selectList(wrapper);
        if (usersList.size() > 0) {
            return usersList.get(0);
        }
        return null;
    }
}
