package vip.proyi.miaosha.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vip.proyi.miaosha.base.ResponseCode;
import vip.proyi.miaosha.comment.MiaoShaUserKey;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.exception.ServiceException;
import vip.proyi.miaosha.mapper.IMiaoShaUserMapper;
import vip.proyi.miaosha.service.IMiaoShaUserService;
import vip.proyi.miaosha.utils.MD5Util;
import vip.proyi.miaosha.utils.RedisUtil;
import vip.proyi.miaosha.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.util.UUID;

@Service
@Slf4j
public class MiaoShaUserServiceImpl extends ServiceImpl<IMiaoShaUserMapper, MiaoShaUser> implements IMiaoShaUserService {

    public static final String COOKI_NAME_TOKEN = "token";

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (null == loginVo) {
            log.error("[用户登录] 登入信息为空");
            throw new ServiceException(ResponseCode.SERVER_ERROR);
        }

        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        //判断手机号是否存在
        MiaoShaUser user = getOne(new QueryWrapper<MiaoShaUser>().lambda().eq(MiaoShaUser::getLoginName, mobile));
        if (null == user) {
            throw new ServiceException(ResponseCode.MOBILE_NOT_EXIST);
        }

        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new ServiceException(ResponseCode.PASSWORD_ERROR);
        }

        // 生成Cookie
        String token = UUID.randomUUID().toString().replace("-", "");
        addCookie(response, token, user);

        return true;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoShaUser user) {
        redisUtil.setObj(MiaoShaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
