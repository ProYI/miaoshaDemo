package vip.proyi.miaosha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.proyi.miaosha.base.ResponseModel;
import vip.proyi.miaosha.entity.User;
import vip.proyi.miaosha.service.IUserService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/demo")
public class SampleController {
    @Resource
    private IUserService userService;

    @GetMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "miaosha");
        return "hello";
    }

    @GetMapping("/db/test")
    @ResponseBody
    public ResponseModel dbTest() {
        User user = new User();
        user.setName("李四");
        userService.save(user);
        User result = userService.getByName("李四");
        return ResponseModel.createBySuccess(result);
    }
}
