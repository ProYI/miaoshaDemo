package vip.proyi.miaosha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.proyi.miaosha.entity.User;
import vip.proyi.miaosha.mapper.IUserMapper;
import vip.proyi.miaosha.service.IUserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {
    @Resource
    private IUserMapper userMapper;

    @Override
    public User getByName(String name) {
        return userMapper.getByName(name);
    }
}
