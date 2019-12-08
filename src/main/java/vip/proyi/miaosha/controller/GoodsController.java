package vip.proyi.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.service.IGoodsService;
import vip.proyi.miaosha.service.IMiaoShaUserService;
import vip.proyi.miaosha.vo.GoodsVo;

import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IMiaoShaUserService userService;
    @Autowired
    IGoodsService goodsService;

    @GetMapping("/to_list")
    public String list(Model model, MiaoShaUser user) {
        model.addAttribute("user", user);

        // 查询商品列表
        List<GoodsVo> goodsList = goodsService.goodsVoList();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @GetMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoShaUser user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        // 秒杀时间
        long startAt = goods.getStartDate().toInstant().getEpochSecond();
        long endAt = goods.getEndDate().toInstant().getEpochSecond();
        // 秒级时间戳 北京时间比UTC时间晚8小时
        long now = Instant.now().getEpochSecond();

        int miaoShaStatus = 0;
        int remainSeconds = 0;

        if (now < startAt) {
            // 秒杀未开始，倒计时
            miaoShaStatus = 0;
            remainSeconds = (int) (startAt - now);
        } else if (now > endAt) {
            // 秒杀已结束
            miaoShaStatus = 2;
            remainSeconds = -1;
        } else {
            // 秒杀进行中
            miaoShaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoShaStatus", miaoShaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
