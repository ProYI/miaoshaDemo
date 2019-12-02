package vip.proyi.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.service.IMiaoShaUserService;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IMiaoShaUserService userService;

    @GetMapping("/to_list")
    public String list(Model model, MiaoShaUser user) {
        model.addAttribute("user", user);
        return "goods_list";
    }
}
