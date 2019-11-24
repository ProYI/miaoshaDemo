package vip.proyi.miaosha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.proyi.miaosha.entity.User;
import vip.proyi.miaosha.mapper.IUserMapper;

import javax.annotation.Resource;

public interface IUserService extends IService<User> {

    User getByName(String name);

}
