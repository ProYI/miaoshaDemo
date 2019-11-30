package vip.proyi.miaosha.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.proyi.miaosha.base.ResponseModel;
import vip.proyi.miaosha.utils.RedisUtil;
import vip.proyi.miaosha.vo.LoginVo;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public ResponseModel<Boolean> doLogin(@Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        return null;
    }
}
