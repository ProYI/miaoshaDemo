package vip.proyi.miaosha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.proyi.miaosha.entity.User;

public interface IUserService extends IService<User> {

    User getByName(String name);

}
