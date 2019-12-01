package vip.proyi.miaosha.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.proyi.miaosha.base.ResponseModel;
import vip.proyi.miaosha.service.IMiaoShaUserService;
import vip.proyi.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    IMiaoShaUserService userService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public ResponseModel<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        userService.login(response, loginVo);
        return null;
    }
}
