package vip.proyi.miaosha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface IMiaoShaUserService extends IService<MiaoShaUser> {
    boolean login(HttpServletResponse response, LoginVo loginVo);

    MiaoShaUser getByToken(HttpServletResponse response, String token);
}
