package vip.proyi.miaosha.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.proyi.miaosha.base.ResponseModel;
import vip.proyi.miaosha.comment.UserKey;
import vip.proyi.miaosha.entity.User;
import vip.proyi.miaosha.service.IUserService;
import vip.proyi.miaosha.utils.RedisUtil;

import javax.annotation.Resource;

@Controller
@RequestMapping("/demo")
@Slf4j
public class SampleController {
    @Resource
    private IUserService userService;
    @Resource
    private RedisUtil redisUtil;

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

    @RequestMapping("/redis/set")
    @ResponseBody
    public ResponseModel<Boolean> redisSet() {
        User user  = new User();
        user.setId(1L);
        user.setName("张三");
        redisUtil.setObj(UserKey.getById, user.getId().toString(), user);
        return ResponseModel.createBySuccess(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public ResponseModel<User> redisGet() {
        User user  = redisUtil.getObj(UserKey.getById, "1", User.class);
        return ResponseModel.createBySuccess(user);
    }

    @RequestMapping("/redis/incr")
    @ResponseBody
    public ResponseModel<String> redisIncr() {
        Long a = redisUtil.incrObj(UserKey.getById, "222");
        log.info("incr 结果{}", a);
        Long b = redisUtil.decrObj(UserKey.getById, "222");
        log.info("decr 结果{}", b);
        return ResponseModel.createBySuccess("incr结束");
    }
}
